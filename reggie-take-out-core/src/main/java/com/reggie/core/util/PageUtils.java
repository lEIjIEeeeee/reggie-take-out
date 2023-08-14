package com.reggie.core.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.core.modular.common.model.request.BaseQueryRequest;

public class PageUtils {

    public static <T> Page<T> createEmptyPage() {
        return new Page<>(1L, 10L);
    }

    public static <T> Page<T> createPage(BaseQueryRequest baseQueryRequest) {
        return new Page<>(baseQueryRequest.getPageNo(), baseQueryRequest.getPageSize());
    }

}
