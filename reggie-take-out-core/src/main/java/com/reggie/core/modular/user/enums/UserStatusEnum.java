package com.reggie.core.modular.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    //
    NORMAL(0, "正常"),
    CONFINE(1, "限制"),
    FREEZE(2, "冻结"),
    DELETE(3, "删除"),
    ;

    private final Integer code;
    private final String name;
}
