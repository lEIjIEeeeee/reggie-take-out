package com.reggie.core.modular.common.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface MyBaseMapper<T> extends BaseMapper<T> {

    int deleteByIdWithFill(T entity);

    int deleteBatchWithFill(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> wrapper);

    Integer insertBatchSomeColumn(Collection<T> entityList);

}
