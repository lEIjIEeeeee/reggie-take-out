package com.reggie.core.modular.demo.manager;

import cn.hutool.core.util.ObjectUtil;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.demo.dao.DemoMapper;
import com.reggie.core.modular.demo.model.entity.Demo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DemoManager {

    private final DemoMapper demoMapper;

    public Demo getDemoById(String id) {
        return demoMapper.selectById(id);
    }

    public Demo getDemoByIdWithExp(String id) {
        Demo demo = demoMapper.selectById(id);
        if (ObjectUtil.isNull(demo)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return demo;
    }
}
