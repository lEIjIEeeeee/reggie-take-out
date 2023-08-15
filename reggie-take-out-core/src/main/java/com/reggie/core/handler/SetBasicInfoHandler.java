package com.reggie.core.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.reggie.core.context.Context;
import com.reggie.core.context.ContextUtils;
import com.reggie.core.modular.auth.model.dto.LoginUser;
import com.reggie.core.modular.common.enums.YesOrNoEnum;
import com.reggie.core.modular.common.model.entity.BaseDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class SetBasicInfoHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object o = metaObject.getOriginalObject();
        if (o instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) o;
            LoginUser currentUser = ContextUtils.getCurrentUser();
            if (currentUser != null) {
                if (StrUtil.isBlank(baseDO.getCreateId())) {
                    baseDO.setCreateId(currentUser.getId());
                }
                if (StrUtil.isBlank(baseDO.getUpdateId())) {
                    baseDO.setUpdateId(currentUser.getId());
                }
            }
            if (baseDO.getCreateTime() == null) {
                baseDO.setCreateTime(DateUtil.date());
            }
            if (baseDO.getUpdateTime() == null) {
                baseDO.setUpdateTime(DateUtil.date());
            }
            if (baseDO.getIsDel() == null) {
                baseDO.setIsDel(YesOrNoEnum.N.getCode());
            }
        } else {
            strictInsertFill(metaObject, BaseDO.CREATE_TIME, Date.class, DateUtil.date());
            strictInsertFill(metaObject, BaseDO.UPDATE_TIME, Date.class, DateUtil.date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object o = metaObject.getOriginalObject();
        if (o instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) o;
            LoginUser currentUser = ContextUtils.getCurrentUser();
            if (currentUser != null) {
                if (StrUtil.isBlank(baseDO.getUpdateId())) {
                    baseDO.setUpdateId(currentUser.getId());
                }
            }
            if (baseDO.getUpdateTime() == null) {
                baseDO.setUpdateTime(DateUtil.date());
            }
        } else {
            strictUpdateFill(metaObject, BaseDO.UPDATE_TIME, Date.class, DateUtil.date());
        }
    }
}
