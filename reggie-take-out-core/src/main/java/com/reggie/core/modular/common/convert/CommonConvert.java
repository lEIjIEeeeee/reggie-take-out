package com.reggie.core.modular.common.convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.core.modular.common.model.vo.PageVO;
import com.reggie.core.util.JavaBeanUtils;

public class CommonConvert {

    public static <S, T> PageVO<T> convertPageToPageVO(IPage<S> page, Class<T> responseClass) {
        return PageVO.<T>builder()
                     .pageNo(page.getCurrent())
                     .pageSize(page.getSize())
                     .total(page.getTotal())
                     .dataList(JavaBeanUtils.mapList(page.getRecords(), responseClass))
                     .build();
    }
}
