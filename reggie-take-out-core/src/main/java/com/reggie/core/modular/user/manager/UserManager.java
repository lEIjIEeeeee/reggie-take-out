package com.reggie.core.modular.user.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.user.dao.UserMapper;
import com.reggie.core.modular.user.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserManager {

    private final UserMapper userMapper;

    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    public User getUserByIdWithExp(String id) {
        User user = userMapper.selectById(id);
        if (ObjectUtil.isNull(user)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return user;
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getPhone, phone));
    }

    public User getUserByAccount(String account) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getAccount, account));
    }

}
