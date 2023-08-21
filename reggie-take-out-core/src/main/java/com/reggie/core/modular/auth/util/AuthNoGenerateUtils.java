package com.reggie.core.modular.auth.util;

import cn.hutool.core.util.RandomUtil;

public class AuthNoGenerateUtils {

    public static String generateSalt() {
        return RandomUtil.randomString(4);
    }

}
