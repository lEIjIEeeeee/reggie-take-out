package com.reggie.common.exception;

import com.reggie.common.enums.HttpResultCode;
import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private HttpResultCode httpResultCode;

    public BizException(HttpResultCode httpResultCode) {
        super(httpResultCode.getMessage());
        this.httpResultCode = httpResultCode;
    }

    public BizException(HttpResultCode httpResultCode, String message) {
        super(message);
        this.httpResultCode = httpResultCode;
    }

}
