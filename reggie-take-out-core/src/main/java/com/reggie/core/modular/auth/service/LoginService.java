package com.reggie.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.context.Context;
import com.reggie.core.context.ContextUtils;
import com.reggie.core.context.HttpContext;
import com.reggie.core.modular.auth.constant.AuthConstants;
import com.reggie.core.modular.auth.dao.MenuMapper;
import com.reggie.core.modular.auth.dao.RoleMapper;
import com.reggie.core.modular.auth.dao.RoleMenuRelMapper;
import com.reggie.core.modular.auth.model.dto.LoginUser;
import com.reggie.core.modular.auth.model.entity.Menu;
import com.reggie.core.modular.auth.model.entity.Role;
import com.reggie.core.modular.auth.model.entity.RoleMenuRel;
import com.reggie.core.modular.auth.model.request.LoginRequest;
import com.reggie.core.modular.common.constant.RedisKeyConstants;
import com.reggie.core.modular.common.constant.SystemConstants;
import com.reggie.core.modular.common.enums.EnableOrDisableStatusEnum;
import com.reggie.core.modular.common.enums.YesOrNoEnum;
import com.reggie.core.modular.user.dao.MemberMapper;
import com.reggie.core.modular.user.enums.UserStatusEnum;
import com.reggie.core.modular.user.manager.MemberManager;
import com.reggie.core.modular.user.manager.UserManager;
import com.reggie.core.modular.user.model.entity.Member;
import com.reggie.core.modular.user.model.entity.User;
import com.reggie.core.security.jwt.JwtPayLoad;
import com.reggie.core.security.jwt.JwtTokenUtils;
import com.reggie.core.util.JavaBeanUtils;
import com.reggie.core.util.JedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LoginService {

    private final MemberMapper memberMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuRelMapper roleMenuRelMapper;
    private final UserManager userManager;
    private final MemberManager memberManager;
    private final JedisUtils redisUtils;

    /**
     * 1.校验账号是否存在
     * 2.校验用户状态
     * 3.校验密码
     * 4.组装填充LoginUser（生成token）
     * 5.更新用户最后登录时间
     * 5.设置session，存储到redis缓存
     * 6.添加到cookie
     * 7.设置上下文
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginUser login(LoginRequest request) {
        User user = userManager.getUserByPhone(request.getAccount());
        if (user == null) {
            user = userManager.getUserByAccount(request.getAccount());
        }
        if (user == null) {
            throw new BizException(HttpResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        //TODO 短信验证码校验
        checkUserStatus(user);

        String encryptPwd = encryptPwd(request.getPassword(), user.getSalt());
        if (ObjectUtil.notEqual(encryptPwd, user.getPassword())) {
            throw new BizException(HttpResultCode.USER_ACCOUNT_OR_PASSWORD_ERROR);
        }

        LoginUser loginUser = JavaBeanUtils.map(user, LoginUser.class);
        fillLoginUserInfo(loginUser);

        String token;
        LoginUser redisUser = redisUtils.getObjWithClass(RedisKeyConstants.LOGIN_USER + loginUser.getId(), LoginUser.class);
        if (redisUser != null) {
            token = redisUser.getAuthToken();
            if (JwtTokenUtils.isTokenExpired(token)) {
                token = generateToken(loginUser);
            }
        } else {
            token = generateToken(loginUser);
        }
        loginUser.setAuthToken(token);

        memberMapper.update(Member.builder()
                                  .build(), Wrappers.lambdaUpdate(Member.class)
                                                    .set(Member::getLastLoginTime, DateUtil.date())
                                                    .set(Member::getUpdateTime, DateUtil.date())
                                                    .eq(Member::getId, loginUser.getId()));

        saveLoginUserInfoToRedis(loginUser);

        addUserTokenToCookie(token);

        setContextInfo(loginUser);
        return loginUser;
    }

    private void checkUserStatus(User user) {
        if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "当前用户已被冻结！");
        }
        if (UserStatusEnum.DELETE.getCode().equals(user.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "当前用户已被删除！");
        }
    }

    public static String encryptPwd(String password, String salt) {
        if (StrUtil.isBlank(password)) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "用户密码为空！");
        }
        if (StrUtil.isBlank(salt)) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "密码盐为空！");
        }
        return SecureUtil.md5(password + SystemConstants.APP_ID + salt);
    }

    private void fillLoginUserInfo(LoginUser loginUser) {
        Member member = memberManager.getMemberByIdWithExp(loginUser.getId());
        loginUser.setNickName(member.getNickName())
                 .setRealName(member.getRealName())
                 .setBirthday(member.getBirthday())
                 .setLastLoginTime(DateUtil.date())
                 .setOpenId(member.getOpenId());

        fillUserRolesInfo(loginUser);

        fillUserPermissionsInfo(loginUser);
    }

    private void fillUserRolesInfo(LoginUser loginUser) {
        if (StrUtil.isBlank(loginUser.getRoleId())) {
            return;
        }
        List<String> roleIds = CollUtil.newArrayList(StrUtil.split(loginUser.getRoleId(), StrUtil.COMMA));
        loginUser.setRoleIds(roleIds);

        List<Role> roleList = roleMapper.selectBatchIds(roleIds);
        if (CollUtil.isEmpty(roleList)) {
            return;
        }
        List<String> roleCodes = roleList.stream()
                                         .map(Role::getCode)
                                         .collect(Collectors.toList());
        loginUser.setRoleCodes(roleCodes);
        List<String> roleNames = roleList.stream()
                                         .map(Role::getName)
                                         .collect(Collectors.toList());
        loginUser.setRoleNames(roleNames);
    }

    private void fillUserPermissionsInfo(LoginUser loginUser) {
        if (CollUtil.isEmpty(loginUser.getRoleIds())) {
            return;
        }
        List<RoleMenuRel> roleMenuRelList = roleMenuRelMapper.selectList(Wrappers.lambdaQuery(RoleMenuRel.class)
                                                                                 .in(RoleMenuRel::getRoleId, loginUser.getRoleIds()));
        if (CollUtil.isEmpty(roleMenuRelList)) {
            return;
        }
        Set<String> menuIds = roleMenuRelList.stream()
                                             .map(RoleMenuRel::getMenuId)
                                             .collect(Collectors.toSet());
        List<Menu> menuList = menuMapper.selectList(Wrappers.lambdaQuery(Menu.class)
                                                            .in(Menu::getId, menuIds)
                                                            .eq(Menu::getMenuFlag, YesOrNoEnum.Y.getCode())
                                                            .eq(Menu::getStatus, EnableOrDisableStatusEnum.ENABLE.getCode()));
        if (CollUtil.isEmpty(menuList)) {
            return;
        }
        List<String> permissions = menuList.stream()
                                           .map(Menu::getCode)
                                           .collect(Collectors.toList());
        loginUser.setPermissions(permissions);
    }

    private String generateToken(LoginUser loginUser) {
        JwtPayLoad jwtPayLoad = JwtPayLoad.builder()
                                          .userId(loginUser.getId())
                                          .account(loginUser.getAccount())
                                          .phone(loginUser.getPhone())
                                          .build();
        return JwtTokenUtils.generateToken(jwtPayLoad);
    }

    private void saveLoginUserInfoToRedis(LoginUser loginUser) {
        redisUtils.setObj(RedisKeyConstants.LOGIN_USER + loginUser.getId(), loginUser);
    }

    private void addUserTokenToCookie(String token) {
        HttpServletResponse response = HttpContext.getResponse();
        if (response != null) {
            Cookie cookie = new Cookie(AuthConstants.TOKEN_NAME, token);
            cookie.setMaxAge(Math.toIntExact(JwtTokenUtils.getExpireSeconds()));
            cookie.setHttpOnly(true);
            cookie.setPath(StrUtil.SLASH);
            response.addCookie(cookie);
        }
    }

    private void setContextInfo(LoginUser loginUser) {
        ContextUtils.setContext(Context.builder()
                                       .currentUser(loginUser)
                                       .build());
    }

}
