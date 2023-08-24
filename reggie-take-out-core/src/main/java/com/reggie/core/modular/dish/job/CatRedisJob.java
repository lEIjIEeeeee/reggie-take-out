package com.reggie.core.modular.dish.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.reggie.core.modular.common.constant.RedisKeyConstants;
import com.reggie.core.modular.dish.dao.CatMapper;
import com.reggie.core.modular.dish.model.entity.Cat;
import com.reggie.core.modular.dish.service.CategoryService;
import com.reggie.core.util.JedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class CatRedisJob {

    private final CatMapper catMapper;
    private final CategoryService categoryService;
    private final JedisUtils redisUtils;

    //TODO 待优化-XxlJob调度
    @Scheduled(cron = "0 0/30 * * * ?")
    public void saveCatToRedis() {
        log.info("saveCatToRedis starting...");
        List<Cat> catList = catMapper.selectList(Wrappers.emptyWrapper());
        if (CollUtil.isEmpty(catList)) {
            log.error("saveCatToRedis failed...");
        }

        List<Tree<String>> treeList = categoryService.buildCatTreeList(catList);
        if (CollUtil.isEmpty(treeList)) {
            log.error("saveCatToRedis failed...");
        }

        redisUtils.setObj(RedisKeyConstants.CAT_NAME, treeList);
    }
}
