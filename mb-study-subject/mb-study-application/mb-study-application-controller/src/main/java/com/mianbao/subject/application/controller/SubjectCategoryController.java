package com.mianbao.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.mianbao.subject.application.convert.SubjectCategoryDTOConverter;
import com.mianbao.subject.application.convert.SubjectLabelDTOConverter;
import com.mianbao.subject.application.dto.SubjectCategoryDTO;

import com.mianbao.subject.application.dto.SubjectLabelDTO;
import com.mianbao.subject.common.entity.Result;
import com.mianbao.subject.common.util.LoginUtil;
import com.mianbao.subject.domain.entity.SubjectCategoryBO;
import com.mianbao.subject.domain.service.SubjectCategoryDomainService;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 题目分类控制层
 * @Author:bread
 * @Date: 2024-07-30 9:43
 */

@RestController
@RequestMapping("/subject/category")
@Slf4j
@CrossOrigin(origins = "http://localhost:8003", allowCredentials = "true")
public class SubjectCategoryController {
    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * 新增分类
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.add.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            // StringUtils.isBlank来检查字符串是否为空白，StringUtils.isBlank 会返回 true 如果字符串为 null
            Preconditions.checkArgument(!StringUtils.isBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.success(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增分类失败");
        }
    }
    /**
     * 查询分类
     * @return
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryPrimaryCategory.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO=SubjectCategoryDTOConverter.INSTANCE
                    .convertDtoToCategoryBO(subjectCategoryDTO);
            List<SubjectCategoryBO> categoryBOS = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOS=SubjectCategoryDTOConverter.INSTANCE
                    .convertBoToCategoryDTOList(categoryBOS);
            return Result.success(subjectCategoryDTOS);
        }catch (Exception e){
            log.error("SubjectCategoryController.query.error:{}", e.getMessage(), e);
            return Result.fail("查询分类失败");
        }

    }

    /**
     * 查询大类下分类
     * @return
     */
    @PostMapping("/queryCategoryByPrimary")
    public Result<List<SubjectCategoryDTO>> queryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryByPrimary.add.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO=SubjectCategoryDTOConverter.INSTANCE
                    .convertDtoToCategoryBO(subjectCategoryDTO);

            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(),"分类Id不能为空");
            List<SubjectCategoryBO> categoryBOS = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOS=SubjectCategoryDTOConverter.INSTANCE
                    .convertBoToCategoryDTOList(categoryBOS);
            return Result.success(subjectCategoryDTOS);
        }catch (Exception e){
            log.error("SubjectCategoryController.query.error:{}", e.getMessage(), e);
            return Result.fail("查询分类失败");
        }

    }

    /**
     * 更新分类操作
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.update.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO=SubjectCategoryDTOConverter.INSTANCE
                    .convertDtoToCategoryBO(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);
            return Result.success(result);
        }catch (Exception e){
            log.error("SubjectCategoryController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新失败");
        }
    }

    /**
     * 删除分类
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectCategoryDTO subjectCategoryDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.delete.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO=SubjectCategoryDTOConverter.INSTANCE
                    .convertDtoToCategoryBO(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);
            return Result.success(result);
        }catch (Exception e){
            log.error("SubjectCategoryController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除失败");
        }
    }
    /**
     * 查询分类及标签一次性
     */
    @PostMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> queryCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryAndLabel.dto:{}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDtoToCategoryBO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryAndLabel(subjectCategoryBO);
            List<SubjectCategoryDTO> dtoList = new LinkedList<>();
            subjectCategoryBOList.forEach(bo -> {
                SubjectCategoryDTO dto = SubjectCategoryDTOConverter.INSTANCE.convertBoToCategoryDTO(bo);
                List<SubjectLabelDTO> labelDTOList = SubjectLabelDTOConverter.INSTANCE.convertBoToLabelDTOList(bo.getLabelBOList());
                dto.setLabelDTOList(labelDTOList);
                dtoList.add(dto);
            });
            String loginId= LoginUtil.getLoginId();
            System.out.println("当前的loginId为:"+loginId);
            return Result.success(dtoList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }

}
