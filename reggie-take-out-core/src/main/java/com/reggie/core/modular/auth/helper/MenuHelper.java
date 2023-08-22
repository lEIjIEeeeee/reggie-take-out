package com.reggie.core.modular.auth.helper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.reggie.core.modular.auth.manager.MenuManager;
import com.reggie.core.modular.auth.model.entity.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuHelper {

    private final MenuManager menuManager;

    public void fillMenuParentInfo(Menu menu) {
        if (Menu.TREE_ROOT_ID.equals(menu.getPcode())) {
            menu.setPid(Menu.TREE_ROOT_ID);
            menu.setPcodes(StrUtil.BRACKET_START + Menu.TREE_ROOT_ID + StrUtil.BRACKET_END);
            menu.setLevels(1);
        } else {
            Menu pMenu = menuManager.getMenuByCode(menu.getPcode());
            if (ObjectUtil.isNull(pMenu)) {
                return;
            }
            menu.setPid(pMenu.getId());
            menu.setPcodes(pMenu.getPcodes() + StrUtil.COMMA + StrUtil.BRACKET_START + pMenu.getCode() + StrUtil.BRACKET_END);
            menu.setLevels(pMenu.getLevels() + 1);
        }
    }

}
