package com.reggie.core.security.jwt;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtPayLoad {

    private String userId;

    private String account;

    private String phone;

    public Map<String, Object> toMap() {
        return BeanUtil.beanToMap(this);
    }

    public static JwtPayLoad toBean(Map<String, Object> map) {
        return BeanUtil.toBean(map, JwtPayLoad.class);
    }
}
