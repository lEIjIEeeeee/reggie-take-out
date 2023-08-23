package com.reggie.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.context.ContextUtils;
import com.reggie.core.modular.auth.dao.MenuMapper;
import com.reggie.core.modular.auth.helper.MenuHelper;
import com.reggie.core.modular.auth.manager.MenuManager;
import com.reggie.core.modular.auth.model.dto.MenuDetailDTO;
import com.reggie.core.modular.auth.model.entity.Menu;
import com.reggie.core.modular.auth.model.request.MenuAddRequest;
import com.reggie.core.modular.auth.model.request.MenuEditRequest;
import com.reggie.core.modular.auth.model.request.MenuIdRequest;
import com.reggie.core.modular.auth.model.request.MenuQueryRequest;
import com.reggie.core.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MenuService {

    private final MenuMapper menuMapper;

    private final MenuManager menuManager;

    private final MenuHelper menuHelper;

    public Tree<String> menuFullTree(MenuQueryRequest request) {
        List<Menu> menuList = getMenuList(request);
        if (CollUtil.isEmpty(menuList)) {
            return Menu.buildTreeRoot();
        }

        return buildMenuTree(menuList);
    }

    public MenuDetailDTO get(String id) {
        Menu menu = menuManager.getByIdWithExp(id);
        MenuDetailDTO menuDetailDTO = JavaBeanUtils.map(menu, MenuDetailDTO.class);
        Menu pMenu = menuManager.getMenuByCode(menu.getPcode());
        if (ObjectUtil.isNull(pMenu)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        menuDetailDTO.setPname(pMenu.getName());
        return menuDetailDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(MenuAddRequest request) {
        String userId = ContextUtils.getCurrentUserId();

        Menu menuByCode = menuManager.getMenuByCode(request.getCode());
        if (ObjectUtil.isNotNull(menuByCode)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }

        Menu menu = JavaBeanUtils.map(request, Menu.class);
        menu.setMenuFlag(request.getMenuFlag().getCode())
            .setSystemType(request.getSystemType().name())
            .setCreateId(userId)
            .setCreateTime(DateUtil.date())
            .setUpdateId(userId)
            .setUpdateTime(DateUtil.date());
        menuHelper.fillMenuParentInfo(menu);
        menuMapper.insert(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(MenuEditRequest request) {
        Menu menu = menuManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, menu);
        menu.setMenuFlag(request.getMenuFlag().getCode())
            .setSystemType(request.getSystemType().name())
            .setUpdateId(ContextUtils.getCurrentUserId())
            .setUpdateTime(DateUtil.date());
        menuHelper.fillMenuParentInfo(menu);
        menuMapper.updateById(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(MenuIdRequest request) {
        Menu menu = menuManager.getByIdWithExp(request.getId());
        menuMapper.deleteByIdWithFill(menu);
    }

    private Tree<String> buildMenuTree(List<Menu> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return Menu.buildTreeRoot();
        }

        //TODO 待优化
        List<Tree<String>> treeList = buildMenuTreeList(menuList);
        if (CollUtil.isEmpty(treeList)) {
            menuList.forEach(menu -> menu.setPid(Menu.TREE_ROOT_ID));
            treeList = buildMenuTreeList(menuList);
        }
        return Menu.buildTreeRoot(treeList);
    }

    private List<Tree<String>> buildMenuTreeList(List<Menu> menuList) {
        return TreeUtil.build(menuList, Menu.TREE_ROOT_ID, Menu.getTreeNodeConfig(),
                (menu, treeNode) -> {
                    treeNode.setId(menu.getId());
                    treeNode.setParentId(menu.getPid());
                    treeNode.setName(menu.getName());
                    treeNode.setWeight(menu.getSort());
                    treeNode.putExtra(Menu.CODE, menu.getCode());
                    treeNode.putExtra(Menu.PCODE, menu.getPcode());
                    treeNode.putExtra(Menu.MENU_FLAG, menu.getMenuFlag());
                    treeNode.setChildren(null);
                });
    }

    private List<Menu> getMenuList(MenuQueryRequest request) {
        return menuMapper.selectList(Wrappers.lambdaQuery(Menu.class)
                                             .and(StrUtil.isNotBlank(request.getKeywords()), wrapper -> wrapper.like(Menu::getName, request.getKeywords())
                                                                                                               .or()
                                                                                                               .like(Menu::getCode, request.getKeywords()))
                                             .eq(request.getLevels() != null, Menu::getLevels, request.getLevels() != null ? request.getLevels() : null)
                                             .eq(request.getMenuFlag() != null, Menu::getMenuFlag, request.getMenuFlag() != null ? request.getMenuFlag().getCode() : null)
                                             .orderByAsc(Menu::getSort));
    }

}
