package com.reggie.core.modular.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.auth.dao.MenuMapper;
import com.reggie.core.modular.auth.model.entity.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuManager {

    private final MenuMapper menuMapper;

    public Menu getByIdWithExp(String id) {
        Menu menu = menuMapper.selectById(id);
        if (ObjectUtil.isNull(menu)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return menu;
    }

    public Menu getMenuByCode(String code) {
        return menuMapper.selectOne(Wrappers.lambdaQuery(Menu.class)
                                            .eq(Menu::getCode, code));
    }
}
