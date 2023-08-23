package com.reggie.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.auth.dao.RoleMapper;
import com.reggie.core.modular.auth.dao.RoleMenuRelMapper;
import com.reggie.core.modular.auth.manager.RoleManager;
import com.reggie.core.modular.auth.manager.RoleMenuRelManager;
import com.reggie.core.modular.auth.model.dto.RoleRelationMenusDTO;
import com.reggie.core.modular.auth.model.entity.Role;
import com.reggie.core.modular.auth.model.entity.RoleMenuRel;
import com.reggie.core.modular.auth.model.request.RoleQueryRequest;
import com.reggie.core.modular.auth.model.request.RoleRequest;
import com.reggie.core.modular.auth.model.request.SetUpRolePermissionsRequest;
import com.reggie.core.modular.common.model.entity.BaseDO;
import com.reggie.core.util.JavaBeanUtils;
import com.reggie.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleMenuRelMapper roleMenuRelMapper;
    private final RoleManager roleManager;
    private final RoleMenuRelManager roleMenuRelManager;

    public Page<Role> listPage(RoleQueryRequest request) {
        return roleMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Role.class)
                                                                            .like(StrUtil.isNotBlank(request.getRoleName()), Role::getName, request.getRoleName())
                                                                            .orderByAsc(Role::getSort)
                                                                            .orderByDesc(Role::getUpdateTime));
    }

    public Role get(String id) {
        return roleManager.getByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(RoleRequest request) {
        Role role = JavaBeanUtils.map(request, Role.class, BaseDO.ID);
        roleMapper.insert(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(RoleRequest request) {
        Role role = roleManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, role);
        roleMapper.updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> idList) {
        roleMapper.deleteBatchWithFill(Role.builder()
                                           .build(), Wrappers.lambdaQuery(Role.class)
                                                             .in(Role::getId, idList));
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUpRolePermissions(SetUpRolePermissionsRequest request) {
        Role role = roleManager.getByIdWithExp(request.getRoleId());
        if (CollUtil.isEmpty(request.getMenuIdList())) {
            return;
        }

        List<RoleMenuRel> roleMenuRelOldList = roleMenuRelManager.listRelByRole(role.getId());
        if (CollUtil.isNotEmpty(roleMenuRelOldList)) {
            List<String> ids = roleMenuRelOldList.stream().map(RoleMenuRel::getId).collect(Collectors.toList());
            roleMenuRelMapper.deleteBatchWithFill(RoleMenuRel.builder()
                                                             .build(), Wrappers.lambdaQuery(RoleMenuRel.class)
                                                                               .in(RoleMenuRel::getId, ids));
        }

        List<RoleMenuRel> roleMenuRelList = CollUtil.newArrayList();
        for (String menuId : request.getMenuIdList()) {
            RoleMenuRel roleMenuRel = RoleMenuRel.builder()
                                                 .roleId(role.getId())
                                                 .menuId(menuId)
                                                 .build();
            roleMenuRelList.add(roleMenuRel);
        }

        if (CollUtil.isEmpty(roleMenuRelList)) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION);
        }
        roleMenuRelMapper.insertBatchSomeColumn(roleMenuRelList);
    }

    public RoleRelationMenusDTO listMenuIdsByRole(String id) {
        Role role = roleManager.getByIdWithExp(id);
        List<RoleMenuRel> roleMenuRelList = roleMenuRelManager.listRelByRole(role.getId());

        List<String> menuIdList = Collections.emptyList();
        if (CollUtil.isNotEmpty(roleMenuRelList)) {
            menuIdList = roleMenuRelList.stream()
                                        .map(RoleMenuRel::getMenuId)
                                        .collect(Collectors.toList());
        }
        return RoleRelationMenusDTO.builder()
                                   .menuIdList(menuIdList)
                                   .build();
    }

}
