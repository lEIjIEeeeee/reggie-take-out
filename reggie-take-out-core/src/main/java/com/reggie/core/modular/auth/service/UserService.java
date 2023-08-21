package com.reggie.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.context.ContextUtils;
import com.reggie.core.modular.auth.model.request.SetUpRolesRequest;
import com.reggie.core.modular.auth.model.request.UserAddRequest;
import com.reggie.core.modular.auth.model.request.UserEditRequest;
import com.reggie.core.modular.auth.util.AuthNoGenerateUtils;
import com.reggie.core.modular.common.model.request.BaseQueryRequest;
import com.reggie.core.modular.user.dao.MemberMapper;
import com.reggie.core.modular.user.dao.UserMapper;
import com.reggie.core.modular.user.enums.UserStatusEnum;
import com.reggie.core.modular.user.manager.MemberManager;
import com.reggie.core.modular.user.manager.UserManager;
import com.reggie.core.modular.user.model.entity.Member;
import com.reggie.core.modular.user.model.entity.User;
import com.reggie.core.util.JavaBeanUtils;
import com.reggie.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final MemberMapper memberMapper;
    private final UserManager userManager;
    private final MemberManager memberManager;

    public Page<User> listPage(BaseQueryRequest request) {
        return userMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(User.class)
                                                                            .orderByDesc(User::getUpdateTime));
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(UserAddRequest request) {
        User userByAccount = userManager.getUserByAccount(request.getAccount());
        if (userByAccount != null) {
            throw new BizException(HttpResultCode.DATA_EXISTED, "该账号已存在！");
        }
        User userByPhone = userManager.getUserByPhone(request.getPhone());
        if (userByPhone != null) {
            throw new BizException(HttpResultCode.DATA_EXISTED, "该手机号已被注册！");
        }

        if (ObjectUtil.notEqual(request.getPassword(), request.getConfirmPwd())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "两次输入的密码不一致！");
        }

        String salt = AuthNoGenerateUtils.generateSalt();
        User user = User.builder()
                        .account(request.getAccount())
                        .password(LoginService.encryptPwd(request.getPassword(), salt))
                        .salt(salt)
                        .name(request.getNickName())
                        .phone(request.getPhone())
                        .gender(request.getGender().name())
                        .email(request.getEmail())
                        .status(UserStatusEnum.NORMAL.getCode())
                        .createId(ContextUtils.getCurrentUserId())
                        .createTime(DateUtil.date())
                        .updateId(ContextUtils.getCurrentUserId())
                        .updateTime(DateUtil.date())
                        .build();
        userMapper.insert(user);

        Member member = Member.builder()
                              .id(user.getId())
                              .account(request.getAccount())
                              .nickName(request.getNickName())
                              .phone(request.getPhone())
                              .gender(request.getGender().name())
                              .birthday(request.getBirthday())
                              .status(UserStatusEnum.NORMAL.getCode())
                              .build();
        memberMapper.insert(member);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(UserEditRequest request) {
        User user = userManager.getUserByIdWithExp(request.getId());
        JavaBeanUtils.map(request, user);
        if (StrUtil.isNotBlank(request.getNickName())) {
            user.setName(request.getNickName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender().name());
        }
        user.setUpdateId(ContextUtils.getCurrentUserId())
            .setUpdateTime(DateUtil.date());
        userMapper.updateById(user);

        Member member = memberManager.getMemberByIdWithExp(request.getId());
        JavaBeanUtils.map(request, member);
        if (request.getGender() != null) {
            member.setGender(request.getGender().name());
        }
        memberMapper.updateById(member);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUpRoles(SetUpRolesRequest request) {
        if (CollUtil.isEmpty(request.getRoleIds())) {
            return;
        }
        User user = userManager.getUserById(request.getUserId());
        if (user == null) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        String roleId = String.join(StrUtil.COMMA, request.getRoleIds());
        userMapper.update(User.builder()
                              .build(), Wrappers.lambdaUpdate(User.class)
                                                .set(User::getRoleId, roleId)
                                                .set(User::getUpdateId, ContextUtils.getCurrentUserId())
                                                .set(User::getUpdateTime, DateUtil.date())
                                                .eq(User::getId, request.getUserId()));
    }

}
