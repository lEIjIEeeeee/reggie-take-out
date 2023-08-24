package com.reggie.core.modular.dish.dao;

import com.reggie.core.modular.common.dao.MyBaseMapper;
import com.reggie.core.modular.dish.model.entity.Cat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CatMapper extends MyBaseMapper<Cat> {
}