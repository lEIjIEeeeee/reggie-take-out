package com.reggie.core.handler;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class GlobalIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        //TODO 全局唯一id生成策略（uuid）
        return IdentifierGenerator.super.nextUUID(entity);
    }
}
