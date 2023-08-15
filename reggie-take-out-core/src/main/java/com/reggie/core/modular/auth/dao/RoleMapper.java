package com.reggie.core.modular.auth.dao;

import com.reggie.core.modular.auth.model.entity.Role;
import com.reggie.core.modular.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends MyBaseMapper<Role> {
}