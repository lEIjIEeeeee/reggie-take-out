package com.reggie.core.modular.user.dao;

import com.reggie.core.modular.common.dao.MyBaseMapper;
import com.reggie.core.modular.user.model.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends MyBaseMapper<Member> {
}