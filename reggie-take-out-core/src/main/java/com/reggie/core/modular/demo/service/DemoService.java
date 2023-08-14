package com.reggie.core.modular.demo.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.core.modular.demo.dao.DemoMapper;
import com.reggie.core.modular.demo.manager.DemoManager;
import com.reggie.core.modular.demo.model.entity.Demo;
import com.reggie.core.modular.demo.model.request.DemoQueryRequest;
import com.reggie.core.modular.demo.model.request.DemoRequest;
import com.reggie.core.util.JavaBeanUtils;
import com.reggie.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DemoService {

    private final DemoMapper demoMapper;
    private final DemoManager demoManager;

    public Page<Demo> listPage(DemoQueryRequest request) {
        return demoMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Demo.class)
                                                                            .like(StrUtil.isNotBlank(request.getName()), Demo::getName, request.getName())
                                                                            .like(StrUtil.isNotBlank(request.getPhone()),Demo::getPhone, request.getPhone())
                                                                            .orderByDesc(Demo::getUpdateTime));

    }

    public Demo get(String id) {
        return demoManager.getDemoByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(DemoRequest request) {
        Demo demo = JavaBeanUtils.map(request, Demo.class);
        demoMapper.insert(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(DemoRequest request) {
        Demo demo = demoManager.getDemoByIdWithExp(request.getId());
        demo.setName(request.getName());
        demo.setPhone(request.getPhone());
        demoMapper.updateById(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> idList) {
        demoMapper.deleteBatchWithFill(Demo.builder()
                                           .build(), Wrappers.lambdaUpdate(Demo.class)
                                                             .in(Demo::getId, idList));
    }

}
