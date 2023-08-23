package com.reggie.core.modular.auth.manager;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.core.modular.auth.dao.RoleMenuRelMapper;
import com.reggie.core.modular.auth.model.entity.RoleMenuRel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RoleMenuRelManager {

    private final RoleMenuRelMapper roleMenuRelMapper;

    public List<RoleMenuRel> listRelByRole(String roleId) {
        return roleMenuRelMapper.selectList(Wrappers.lambdaQuery(RoleMenuRel.class)
                                                    .eq(RoleMenuRel::getRoleId, roleId));
    }

}
