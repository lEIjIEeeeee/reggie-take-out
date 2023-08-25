package com.reggie.core.modular.dish.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.dish.dao.CatMapper;
import com.reggie.core.modular.dish.model.entity.Cat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CatManager {

    private final CatMapper catMapper;

    public Cat getById(String id) {
        return catMapper.selectById(id);
    }

    public Cat getByIdWithExp(String id) {
        Cat cat = catMapper.selectById(id);
        if (ObjectUtil.isNull(cat)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return cat;
    }

    public Cat getCatByName(String name) {
        return catMapper.selectOne(Wrappers.lambdaQuery(Cat.class)
                                           .eq(Cat::getName, name));
    }

}
