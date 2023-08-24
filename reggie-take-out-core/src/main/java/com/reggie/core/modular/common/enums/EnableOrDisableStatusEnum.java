package com.reggie.core.modular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableOrDisableStatusEnum {
    ENABLE(1, "启用"),
    DISABLE(2, "禁用"),
    ;

    private final Integer code;
    private final String name;
}
