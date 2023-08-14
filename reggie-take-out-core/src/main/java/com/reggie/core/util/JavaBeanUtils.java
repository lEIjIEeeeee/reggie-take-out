package com.reggie.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JavaBeanUtils {

    public static <T> T map(Object source, Class<T> target, String... ignoreProperties) {
        try {
            T result = target.newInstance();
            copyNonNullProperties(source, result, ignoreProperties);
            return result;
        } catch (Exception e) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "对象转换异常");
        }
    }

    public static void map(Object source, Object target, String... ignoreProperties) {
        copyNonNullProperties(source, target, ignoreProperties);
    }

    public static <T> List<T> mapList(List<?> sourceList, Class<T> target) {
        if (CollUtil.isEmpty(sourceList)) {
            return Collections.emptyList();
        }

        List<T> resultList = CollUtil.newArrayList();
        sourceList.forEach(src -> resultList.add(map(src, target)));
        return resultList;
    }

    private static void copyNonNullProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtil.copyProperties(source, target, ArrayUtil.addAll(ignoreProperties, getEmptyPropertyNames(source)));
    }

    private static String[] getEmptyPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = CollUtil.newHashSet();
        for (PropertyDescriptor pd : pds) {
            Object srcPropertyValue = src.getPropertyValue(pd.getName());
            if (srcPropertyValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return ArrayUtil.toArray(emptyNames, String.class);
    }

}
