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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CatRedisJob {

    private final CatMapper catMapper;
    private final CategoryService categoryService;
    private final JedisUtils redisUtils;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveCatToRedis() {
        List<Cat> catList = catMapper.selectList(Wrappers.emptyWrapper());
        if (CollUtil.isEmpty(catList)) {
            return;
        }

        List<Tree<String>> treeList = categoryService.buildCatTreeList(catList);
        if (CollUtil.isEmpty(treeList)) {
            return;
        }

        redisUtils.setObj(RedisKeyConstants.CAT_NAME, treeList);
    }

}
