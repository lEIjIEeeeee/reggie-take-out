package com.reggie.core.modular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {
    //
    N(0, "否"),
    Y(1, "是"),
    ;

    private final Integer code;
    private final String name;
}
