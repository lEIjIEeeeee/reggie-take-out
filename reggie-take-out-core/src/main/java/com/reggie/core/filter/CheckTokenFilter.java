package com.reggie.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.common.model.HttpResult;
import com.reggie.core.config.WebSecurityConfig;
import com.reggie.core.context.Context;
import com.reggie.core.context.ContextUtils;
import com.reggie.core.modular.auth.constant.AuthConstants;
import com.reggie.core.modular.auth.model.dto.LoginUser;
import com.reggie.core.modular.common.constant.RedisKeyConstants;
import com.reggie.core.security.jwt.JwtPayLoad;
import com.reggie.core.security.jwt.JwtTokenUtils;
import com.reggie.core.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class CheckTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JedisUtils redisUtils;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setContentType("application/json;CHARSET=UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (isExcludedUrl(request.getRequestURI())
                || request.getRequestURI().contains("/webjars")
                || request.getRequestURI().contains("/swagger-resources")
                || request.getRequestURI().contains("/v2")
                || request.getRequestURI().contains("/druid")) {
            ContextUtils.clear();
            chain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        JwtPayLoad jwtPayLoad;
        try {
            jwtPayLoad = JwtTokenUtils.getJwtPayLoad(token);
        } catch (Exception e) {
            validTokenError(response);
            return;
        }

        if (jwtPayLoad != null) {
            LoginUser loginUser = redisUtils.getObjWithClass(RedisKeyConstants.LOGIN_USER + jwtPayLoad.getUserId(), LoginUser.class);
            if (loginUser == null) {
                validTokenExpired(response);
                return;
            }
            if (ObjectUtil.notEqual(token, loginUser.getAuthToken())) {
                validTokenError(response);
                return;
            }
            setContextInfo(request, loginUser);
            chain.doFilter(request, response);
        } else {
            validTokenError(response);
        }

    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(AuthConstants.TOKEN_NAME);
    }

    private void setContextInfo(HttpServletRequest request, LoginUser loginUser) {
        checkLoginUserInfo(loginUser);
        ContextUtils.setContext(Context.builder()
                                       .currentUser(loginUser)
                                       .build());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void checkLoginUserInfo(LoginUser loginUser) {
        if (StrUtil.isBlank(loginUser.getId())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "用户id信息不存在");
        }
        if (StrUtil.isBlank(loginUser.getPhone())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "用户手机号信息不存在");
        }
        if (StrUtil.isBlank(loginUser.getAccount())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "用户登录账号信息不存在");
        }
    }

    private void validTokenError(ServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HttpResult<Void> failure = HttpResult.failure(HttpResultCode.TOKEN_VALIDATED_ERROR);
            out.write(JSON.toJSONString(failure));
            out.flush();
        } catch (IOException e) {
            log.error("Token校验失败！");
        }
    }

    private void validTokenExpired(ServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HttpResult<Void> failure = HttpResult.failure(HttpResultCode.TOKEN_VALIDATED_EXPIRED);
            out.write(JSON.toJSONString(failure));
            out.flush();
        } catch (IOException e) {
            log.error("Token已过期！");
        }
    }

    private boolean isExcludedUrl(String url) {
        return WebSecurityConfig.EXCLUDED_URL_LIST.contains(StrUtil.replace(url, contextPath, StrUtil.EMPTY));
    }
}
