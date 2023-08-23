package com.reggie.core.modular.common.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.common.dao.DictMapper;
import com.reggie.core.modular.common.dao.DictTypeMapper;
import com.reggie.core.modular.common.manager.DictManager;
import com.reggie.core.modular.common.model.entity.Dict;
import com.reggie.core.modular.common.model.entity.DictType;
import com.reggie.core.modular.common.model.request.*;
import com.reggie.core.util.JavaBeanUtils;
import com.reggie.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class DictService {

    private final DictTypeMapper dictTypeMapper;
    private final DictMapper dictMapper;
    private final DictManager dictManager;

    public Page<DictType> listPageDictType(DictTypeQueryRequest request) {
        return dictTypeMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(DictType.class)
                                                                                .and(StrUtil.isNotBlank(request.getKeywords()), wrapper -> wrapper.like(DictType::getCode, request.getKeywords())
                                                                                                                                                  .or()
                                                                                                                                                  .like(DictType::getName, request.getKeywords()))
                                                                                .eq(request.getDictType() != null, DictType::getType, request.getDictType() != null ? request.getDictType() : null)
                                                                                .eq(request.getStatus() != null, DictType::getStatus, request.getStatus() != null ? request.getStatus() : null)
                                                                                .orderByAsc(DictType::getSort)
                                                                                .orderByDesc(DictType::getUpdateTime));
    }

    public DictType getDictTypeDetail(String id) {
        return dictManager.getDictTypeByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDictType(DictTypeAddRequest request) {
        DictType dictTypeByCode = dictManager.getDictTypeByCode(request.getCode());
        if (ObjectUtil.isNotNull(dictTypeByCode)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
        DictType dictType = JavaBeanUtils.map(request, DictType.class);
        dictType.setType(request.getType().name())
                .setStatus(request.getStatus().name());
        dictTypeMapper.insert(dictType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editDictType(DictTypeEditRequest request) {
        DictType dictType = dictManager.getDictTypeByIdWithExp(request.getId());
        JavaBeanUtils.map(request, dictType);
        dictType.setType(request.getType().name())
                .setStatus(request.getStatus().name());
        dictTypeMapper.updateById(dictType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(DictTypeIdRequest request) {
        DictType dictType = dictManager.getDictTypeByIdWithExp(request.getId());
        dictTypeMapper.deleteByIdWithFill(dictType);
    }

    public Page<Dict> listPageDict(DictQueryRequest request) {
        return dictMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Dict.class)
                                                                            .eq(Dict::getDictTypeId, request.getDictTypeId())
                                                                            .and(StrUtil.isNotBlank(request.getKeywords()), wrapper -> wrapper.like(Dict::getName, request.getKeywords())
                                                                                                                                              .or()
                                                                                                                                              .like(Dict::getCode, request.getKeywords()))
                                                                            .orderByAsc(Dict::getSort)
                                                                            .orderByDesc(Dict::getUpdateTime));
    }

    public Dict getDictDetail(String id) {
        return dictManager.getDictByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDict(DictAddRequest request) {
        Dict dictByCode = dictManager.getDictByCode(request.getDictTypeId(), request.getCode());
        if (ObjectUtil.isNotNull(dictByCode)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
        Dict dict = JavaBeanUtils.map(request, Dict.class);
        dict.setStatus(request.getStatus().name());
        dictMapper.insert(dict);
    }

    @Transactional(rollbackFor = Exception.class)
    public void editDict(DictEditRequest request) {
        Dict dict = dictManager.getDictByIdWithExp(request.getId());
        JavaBeanUtils.map(request, dict);
        dict.setStatus(request.getStatus().name());
        dictMapper.updateById(dict);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDict(DictIdRequest request) {
        Dict dict = dictManager.getDictByIdWithExp(request.getId());
        dictMapper.deleteByIdWithFill(dict);
    }

}
