package com.reggie.core.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.core.modular.common.model.request.BaseQueryRequest;

import java.util.List;

public class PageUtils {

    public static <T> Page<T> createEmptyPage() {
        return new Page<>(1L, 10L);
    }

    public static <T> Page<T> createPage(BaseQueryRequest baseQueryRequest) {
        return new Page<>(baseQueryRequest.getPageNo(), baseQueryRequest.getPageSize());
    }

    public static <T> Page<T> createPage(Long pageNo, Long pageSize, Long total, List<T> records) {
        Page<T> page =  new Page<>(pageNo, pageSize, total);
        page.setRecords(records);
        return page;
    }

}
