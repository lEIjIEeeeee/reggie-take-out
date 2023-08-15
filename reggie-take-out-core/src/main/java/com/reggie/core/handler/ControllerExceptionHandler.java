package com.reggie.core.handler;

import cn.hutool.core.collection.CollUtil;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.model.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public HttpResult<Void> handleParameterNotValidException(Exception e) {
        log.error("handleParameterNotValidException: ", e);

        List<FieldError> errorList = CollUtil.newArrayList();
        if (e instanceof MethodArgumentNotValidException) {
            errorList = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }

        if (e instanceof BindException) {
            errorList = ((BindException) e).getFieldErrors();
        }

        if (CollUtil.isNotEmpty(errorList)) {
            StringBuilder errorMsg = new StringBuilder("校验失败：");
            for (FieldError fieldError : errorList) {
                errorMsg.append(fieldError.getField())
                        .append("：")
                        .append(fieldError.getDefaultMessage())
                        .append("，");
            }
            return HttpResult.failure(HttpResultCode.PARAM_VALIDATED_FAILED, errorMsg.toString());
        } else {
            return HttpResult.failure(HttpResultCode.PARAM_VALIDATED_FAILED);
        }
    }

}
