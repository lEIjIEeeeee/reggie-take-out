package com.reggie.common.model;

import com.reggie.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 6550541203278653993L;

    private Integer code;

    private String message;

    private T data;

    public static <T> HttpResult<T> success() {
        return HttpResult.<T>builder()
                         .code(HttpResultCode.SUCCESS.getCode())
                         .message(HttpResultCode.SUCCESS.getMessage())
                         .build();
    }

    public static <T> HttpResult<T> success(T data) {
        return HttpResult.<T>builder()
                         .code(HttpResultCode.SUCCESS.getCode())
                         .message(HttpResultCode.SUCCESS.getMessage())
                         .data(data)
                         .build();
    }

    public static <T> HttpResult<T> failure(HttpResultCode httpResultCode) {
        return HttpResult.<T>builder()
                         .code(httpResultCode.getCode())
                         .message(httpResultCode.getMessage())
                         .build();
    }

    public static <T> HttpResult<T> failure(HttpResultCode httpResultCode, String message) {
        return HttpResult.<T>builder()
                         .code(httpResultCode.getCode())
                         .message(message)
                         .build();
    }

}
