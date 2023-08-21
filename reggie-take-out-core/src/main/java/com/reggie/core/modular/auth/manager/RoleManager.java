package com.reggie.core.modular.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.auth.dao.RoleMapper;
import com.reggie.core.modular.auth.model.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleManager {

    private final RoleMapper roleMapper;

    public Role getById(String id) {
        return roleMapper.selectById(id);
    }

    public Role getByIdWithExp(String id) {
        Role role = roleMapper.selectById(id);
        if (ObjectUtil.isNull(role)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return role;
    }

}
