package com.reggie.core.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

@Configuration
public class JedisConfig {

    @Value("${jedisPool.masterName}")
    private String masterName;

    @Value("${jedisPool.sentinels}")
    private String sentinels;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Bean
    public JedisSentinelPool jedisSentinelPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setTestWhileIdle(Boolean.TRUE);
        jedisPoolConfig.setTestOnBorrow(Boolean.TRUE);
        jedisPoolConfig.setTestOnReturn(Boolean.TRUE);

        Set<String> jedisSentinels = CollUtil.newHashSet(StrUtil.split(sentinels, StrUtil.COMMA));
        return new JedisSentinelPool(masterName, jedisSentinels, jedisPoolConfig, timeout, password, database);
    }

}
