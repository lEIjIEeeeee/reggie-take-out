package com.reggie.core.modular.demo.dao;

import com.reggie.core.modular.common.dao.MyBaseMapper;
import com.reggie.core.modular.demo.model.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoMapper extends MyBaseMapper<Demo> {
}