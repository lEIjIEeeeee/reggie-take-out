package com.reggie.core.modular.common.helper;

import cn.hutool.core.util.StrUtil;
import com.reggie.core.modular.common.manager.DictManager;
import com.reggie.core.modular.common.model.entity.DictType;
import com.reggie.core.modular.common.model.response.DictDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DictHelper {

    private final DictManager dictManager;

    public void fillDictTypeInfo(DictDetailResponse response) {
        if (StrUtil.isBlank(response.getDictTypeId())) {
            return;
        }
        DictType dictType = dictManager.getDictTypeByIdWithExp(response.getDictTypeId());
        response.setDictTypeName(dictType.getName());
    }

}
