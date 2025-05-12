package com.mianbao.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;

import com.mianbao.subject.domain.entity.SubjectInfoBO;
import org.apache.commons.collections4.MapUtils;
import com.mianbao.subject.common.enums.IsDeleteEnum;
import com.mianbao.subject.domain.convert.SubjectCategoryConverter;
import com.mianbao.subject.domain.entity.SubjectCategoryBO;
import com.mianbao.subject.domain.entity.SubjectLabelBO;
import com.mianbao.subject.domain.service.SubjectCategoryDomainService;
import com.mianbao.subject.domain.util.CacheUtil;
import com.mianbao.subject.infrastructure.basic.entity.SubjectCategory;
import com.mianbao.subject.infrastructure.basic.entity.SubjectLabel;
import com.mianbao.subject.infrastructure.basic.entity.SubjectMapping;
import com.mianbao.subject.infrastructure.basic.service.SubjectCategoryService;

import com.mianbao.subject.infrastructure.basic.service.SubjectLabelService;
import com.mianbao.subject.infrastructure.basic.service.SubjectMappingService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @Description: 刷题分类实现类
 * @Author:bread
 * @Date: 2024-07-30 9:46
 */
@Service
@Slf4j
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private ThreadPoolExecutor labelThreadPool;

    @Resource
    private CacheUtil cacheUtil;

    /**
     * 新增分类
     * @param subjectCategoryBO
     */
    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.add.bo:{}", JSON.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeleteEnum.UN_DELETED.getCode());
        subjectCategoryService.insert(subjectCategory);
    }

    /**
     * 查询分类
     * @return
     */
    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory=SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeleteEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> boList=SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryList);

        return boList;
    }

    /**
     * 更新分类
     * @param subjectCategoryBO
     * @return
     */
    @Override
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory=SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        int updateState=subjectCategoryService.update(subjectCategory);
        return updateState > 0;
    }

    /**
     * 删除分类
     * @param subjectCategoryBO
     * @return
     */
    @Override
    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory=SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeleteEnum.DELETED.getCode());
        return subjectCategoryService.deleteById(subjectCategory.getId());
    }

    @SneakyThrows
    @Override
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        Long id = subjectCategoryBO.getId();
        String cacheKey = "categoryAndLabel." + subjectCategoryBO.getId();
        List<SubjectCategoryBO> subjectCategoryBOS = cacheUtil.getResult(cacheKey,
                SubjectCategoryBO.class, (key) -> getSubjectCategoryBOS(id));
        return subjectCategoryBOS;
    }

    private List<SubjectCategoryBO> getSubjectCategoryBOS(Long categoryId) {
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setParentId(categoryId);
        subjectCategory.setIsDeleted(IsDeleteEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}",
                    JSON.toJSONString(subjectCategoryList));
        }
        List<SubjectCategoryBO> categoryBOList = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryList);
        Map<Long, List<SubjectLabelBO>> map = new HashMap<>();
        // 创建异步任务列表
        //  // 将categoryBOList中的每个元素映射为一个CompletableFuture，该CompletableFuture在labelThreadPool线程池中异步执行getLabelBOList方法
        List<CompletableFuture<Map<Long, List<SubjectLabelBO>>>> completableFutureList =
                categoryBOList.stream().map(category ->
                CompletableFuture.supplyAsync(() -> getLabelBOList(category), labelThreadPool)
        ).collect(Collectors.toList());
        // 遍历CompletableFutureList中的每个CompletableFuture
        completableFutureList.forEach(future -> {
            try {
                Map<Long, List<SubjectLabelBO>> resultMap = future.get();
                if (!MapUtils.isEmpty(resultMap)) {
                    map.putAll(resultMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        categoryBOList.forEach(categoryBO -> {
            if (!CollectionUtils.isEmpty(map.get(categoryBO.getId()))) {
                categoryBO.setLabelBOList(map.get(categoryBO.getId()));
            }
        });
        return categoryBOList;
    }

    private Map<Long, List<SubjectLabelBO>> getLabelBOList(SubjectCategoryBO category) {
        if (log.isInfoEnabled()) {
            log.info("getLabelBOList:{}", JSON.toJSONString(category));
        }
        Map<Long, List<SubjectLabelBO>> labelMap = new HashMap<>();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(category.getId());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        if (CollectionUtils.isEmpty(mappingList)) {
            return null;
        }
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<SubjectLabelBO> labelBOList = new LinkedList<>();
        labelList.forEach(label->{
            SubjectLabelBO subjectLabelBO=new SubjectLabelBO();
            subjectLabelBO.setId(label.getId());
            subjectLabelBO.setLabelName(label.getLabelName());
            subjectLabelBO.setCategoryId(label.getCategoryId());
            subjectLabelBO.setSortNum(label.getSortNum());
            labelBOList.add(subjectLabelBO);
        });
        labelMap.put(category.getId(), labelBOList);
        return labelMap;
    }

}
