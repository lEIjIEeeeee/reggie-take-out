package com.reggie.core.modular.dish.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.dish.dao.CatMapper;
import com.reggie.core.modular.dish.helper.CatHelper;
import com.reggie.core.modular.dish.manager.CatManager;
import com.reggie.core.modular.dish.model.entity.Cat;
import com.reggie.core.modular.dish.model.request.CatIdRequest;
import com.reggie.core.modular.dish.model.request.CatQueryRequest;
import com.reggie.core.modular.dish.model.request.CatRequest;
import com.reggie.core.modular.dish.model.request.CatSetUpStatusRequest;
import com.reggie.core.util.JavaBeanUtils;
import com.reggie.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reggie.core.modular.dish.model.entity.Cat.*;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private final CatMapper catMapper;
    private final CatManager catManager;
    private final CatHelper catHelper;

    public Tree<String> getCatFullTree() {
        List<Cat> catList = getCatList();
        if (CollUtil.isEmpty(catList)) {
            return buildTreeRoot();
        }

        return buildCatTree(catList);
    }

    public Page<Cat> listPage(CatQueryRequest request) {
        return catMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Cat.class)
                                                                           .eq(StrUtil.isNotBlank(request.getId()), Cat::getPid, request.getId())
                                                                           .orderByAsc(Cat::getSort));
    }

    public Cat get(String id) {
        return catManager.getByIdWithExp(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(CatRequest request) {
        checkCatName(request);
        Cat cat = JavaBeanUtils.map(request, Cat.class, Cat.ID);
        cat.setStatus(request.getStatus().getCode());
        catHelper.fillCatInfo(cat);
        catMapper.insert(cat);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(CatRequest request) {
        checkCatName(request);
        Cat cat = catManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, cat);
        cat.setStatus(request.getStatus().getCode());
        catHelper.fillCatInfo(cat);
        catMapper.updateById(cat);
    }

    private void checkCatName(CatRequest request) {
        Cat catByName = catManager.getCatByName(request.getName());
        if (ObjectUtil.isNotNull(catByName)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(CatIdRequest request) {
        Cat cat = catManager.getByIdWithExp(request.getId());
        catMapper.deleteByIdWithFill(cat);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUpStatus(CatSetUpStatusRequest request) {
        Cat cat = catManager.getByIdWithExp(request.getId());
        cat.setStatus(request.getStatus().getCode());
        catMapper.updateById(cat);
    }

    private Tree<String> buildCatTree(List<Cat> catList) {
        if (CollUtil.isEmpty(catList)) {
            return buildTreeRoot();
        }

        List<Tree<String>> treeList = buildCatTreeList(catList);
        if (CollUtil.isEmpty(treeList)) {
            catList.forEach(cat -> cat.setPid(TREE_ROOT_ID));
            treeList = buildCatTreeList(catList);
        }
        return buildTreeRoot(treeList);
    }

    public List<Tree<String>> buildCatTreeList(List<Cat> catList) {
        return TreeUtil.build(catList, TREE_ROOT_ID, getTreeNodeConfig(),
                (cat, treeNode) -> {
                    treeNode.setId(cat.getId());
                    treeNode.setParentId(cat.getPid());
                    treeNode.setName(cat.getName());
                    treeNode.setWeight(cat.getSort());
                    treeNode.putExtra(LEVEL, cat.getLevel());
                    treeNode.setChildren(null);
                });
    }

    private List<Cat> getCatList() {
        return catMapper.selectList(Wrappers.lambdaQuery(Cat.class)
                                            .orderByAsc(Cat::getSort));
    }

}
