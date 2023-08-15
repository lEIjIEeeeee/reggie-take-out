package com.reggie.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpResultCode {
    //
    SUCCESS(0, "成功"),
    SYSTEM_ERROR(999, "系统错误"),

    PARAM_VALIDATED_FAILED(1000, "参数校验错误"),
    TOKEN_VALIDATED_ERROR(1001, "Token校验失败"),
    TOKEN_VALIDATED_EXPIRED(1002, "Token已过期"),

    USER_ACCOUNT_NOT_EXIST(2000, "用户账号不存在"),
    USER_ACCOUNT_OR_PASSWORD_ERROR(2001, "用户账号或密码输入错误"),

    BIZ_EXCEPTION(3000, "业务异常"),
    BIZ_DATA_EXCEPTION(3001, "业务数据异常"),
    DATA_NOT_EXISTED(3002, "数据不存在"),
    DATA_EXISTED(3003, "数据已存在"),
    ;

    private final Integer code;
    private final String message;
}
