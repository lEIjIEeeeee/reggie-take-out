package com.reggie.core.modular.auth.helper;

import cn.hutool.core.util.ObjectUtil;
import com.reggie.core.modular.auth.manager.RoleManager;
import com.reggie.core.modular.auth.model.dto.RoleDetailDTO;
import com.reggie.core.modular.auth.model.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleHelper {

    private final RoleManager roleManager;

    public void fillRoleDetailData(RoleDetailDTO roleDetailDTO) {
        Role role = roleManager.getById(roleDetailDTO.getPid());
        if (ObjectUtil.isNotNull(role)) {
            roleDetailDTO.setPName(role.getName());
        }
    }

}
