package com.reggie.core.modular.common.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.common.dao.DictMapper;
import com.reggie.core.modular.common.dao.DictTypeMapper;
import com.reggie.core.modular.common.model.entity.Dict;
import com.reggie.core.modular.common.model.entity.DictType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DictManager {

    private final DictTypeMapper dictTypeMapper;
    private final DictMapper dictMapper;

    public DictType getDictTypeById(String dictTypeId) {
        return dictTypeMapper.selectById(dictTypeId);
    }

    public DictType getDictTypeByIdWithExp(String dictTypeId) {
        DictType dictType = dictTypeMapper.selectById(dictTypeId);
        if (ObjectUtil.isNull(dictType)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return dictType;
    }

    public DictType getDictTypeByCode(String code) {
        return dictTypeMapper.selectOne(Wrappers.lambdaQuery(DictType.class)
                                                .eq(DictType::getCode, code));
    }

    public Dict getDictById(String dictId) {
        return dictMapper.selectById(dictId);
    }

    public Dict getDictByIdWithExp(String dictId) {
        Dict dict = dictMapper.selectById(dictId);
        if (ObjectUtil.isNull(dict)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return dict;
    }

    public Dict getDictByCode(String dictTypeId, String code) {
        return dictMapper.selectOne(Wrappers.lambdaQuery(Dict.class)
                                            .eq(Dict::getDictTypeId, dictTypeId)
                                            .eq(Dict::getCode, code));
    }

}
