package com.reggie.core.security.jwt;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.reggie.core.modular.common.constant.PropertiesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtTokenUtils {

    public static JwtPayLoad getJwtPayLoad(String token) {
        Claims claims = getClaimsByToken(token);
        return JwtPayLoad.toBean(claims);
    }

    private static String generateToken(String userId, Date expireDate, Map<String, Object> claims) {
        final Date currentDate = new Date();
        String secret = getJwtSecret();
        if (claims == null) {
            return Jwts.builder()
                       .setSubject(userId)
                       .setIssuedAt(currentDate)
                       .setExpiration(expireDate)
                       .signWith(SignatureAlgorithm.HS512, secret)
                       .compact();
        } else {
            return Jwts.builder()
                       .setClaims(claims)
                       .setSubject(userId)
                       .setIssuedAt(currentDate)
                       .setExpiration(expireDate)
                       .signWith(SignatureAlgorithm.HS512, secret)
                       .compact();
        }
    }

    private static Claims getClaimsByToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new IllegalArgumentException("token参数为空");
        }
        String secret = getJwtSecret();
        return Jwts.parser()
                   .setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();
    }

    private static String getJwtSecret() {
        return SpringUtil.getProperty(PropertiesConstants.JWT_SECRET);
    }

}
