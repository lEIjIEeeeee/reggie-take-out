package com.reggie.core.modular.auth.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.reggie.core.modular.auth.dao.RoleMapper;
import com.reggie.core.modular.auth.model.entity.Role;
import com.reggie.core.modular.auth.model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserHelper {

    private final RoleMapper roleMapper;

    public void fillRoleInfoData(List<UserResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        List<String> roleIdList = dataList.stream()
                                          .map(UserResponse::getRoleId)
                                          .filter(StrUtil::isNotBlank)
                                          .collect(Collectors.toList());
        if (CollUtil.isEmpty(roleIdList)) {
            return;
        }

        Set<String> roleIds = CollUtil.newHashSet();
        for (String roleId : roleIdList) {
            if (StrUtil.isNotBlank(roleId)) {
                roleIds.addAll(CollUtil.newArrayList(StrUtil.split(roleId, StrUtil.COMMA)));
            }
        }

        List<Role> roleList = roleMapper.selectBatchIds(roleIds);
        if (CollUtil.isEmpty(roleList)) {
            return;
        }
        Map<String, Role> roleMap = roleList.stream()
                                            .collect(Collectors.toMap(Role::getId, role -> role));

        for (UserResponse response : dataList) {
            String roleId = response.getRoleId();
            List<String> keys = StrUtil.split(roleId, StrUtil.COMMA);

            List<String> roleNameList = CollUtil.newArrayList();
            keys.forEach(key -> {
                if (roleMap.get(key) != null) {
                    roleNameList.add(roleMap.get(key).getName());
                }
            });
            if (CollUtil.isNotEmpty(roleNameList)) {
                response.setRoleNames(String.join(StrUtil.COMMA, roleNameList));
            }
        }
    }

}
