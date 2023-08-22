package com.reggie.core.modular.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wlj
 */

@Getter
@AllArgsConstructor
public enum MenuSystemTypeEnum {
    //
    BASE_SYSTEM("概况"),
    SYSTEM_FUNC("系统"),
    ;

    private final String name;
}
