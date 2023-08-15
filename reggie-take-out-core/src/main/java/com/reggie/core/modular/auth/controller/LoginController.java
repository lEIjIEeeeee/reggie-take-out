package com.reggie.core.modular.auth.controller;

import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.auth.model.dto.LoginUser;
import com.reggie.core.modular.auth.model.request.LoginRequest;
import com.reggie.core.modular.auth.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "权限模块-用户登录接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/login")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public HttpResult<LoginUser> login(@RequestBody @Validated LoginRequest request) {
        return HttpResult.success(loginService.login(request));
    }

}
