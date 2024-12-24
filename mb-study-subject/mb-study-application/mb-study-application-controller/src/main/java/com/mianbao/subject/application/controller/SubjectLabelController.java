package com.mianbao.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.mianbao.subject.application.dto.SubjectCategoryDTO;
import com.mianbao.subject.application.dto.SubjectLabelDTO;
import com.mianbao.subject.application.convert.SubjectLabelDTOConverter;

import com.mianbao.subject.common.entity.Result;
import com.mianbao.subject.domain.entity.SubjectLabelBO;
import com.mianbao.subject.domain.service.SubjectLabelDomainService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 标签控制类
 * @Author:bread
 * @Date: 2024-08-21 17:18
 */
@RestController
@RequestMapping("/subject/label")
@Slf4j
@CrossOrigin(origins = "http://localhost:8003", allowCredentials = "true")
public class SubjectLabelController {

    @Resource
    private SubjectLabelDomainService subjectLabelDomainService;
    /**
     * 新增分类
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.add.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            // StringUtils.isBlank来检查字符串是否为空白，StringUtils.isBlank 会返回 true 如果字符串为 null
            Preconditions.checkArgument(!StringUtils.isBlank(subjectLabelDTO.getLabelName()), "标签名称不能为空");
            Preconditions.checkNotNull(subjectLabelDTO.getSortNum(), "排序不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
          Boolean res =  subjectLabelDomainService.add(subjectLabelBO);
            return Result.success(res);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增标签失败");
        }
    }


    /**
     * 更新标签
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectLabelDTO subjectLabelDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.update.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkNotNull(subjectLabelDTO.getId(),"标签Id不能为空");
            SubjectLabelBO subjectLabelBO= SubjectLabelDTOConverter.INSTANCE
                    .convertDtoToLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.update(subjectLabelBO);
            return Result.success(result);
        }catch (Exception e){
            log.error("SubjectCategoryController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新失败");
        }
    }

    /**
     * 删除标签
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectLabelDTO subjectLabelDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.delete.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            SubjectLabelBO subjectLabelBO=SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.delete(subjectLabelBO);
            return Result.success(result);
        }catch (Exception e){
            log.error("SubjectCategoryController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除失败");
        }
    }


    /**
     * 根据分类id拆线呢标签
     * @return
     */
    @PostMapping("/queryByCategoryId")
    public Result<List<SubjectCategoryDTO>> queryByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO){

        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.queryByCategoryId.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkNotNull(subjectLabelDTO.getCategoryId(),"分类Id不能为空");
            SubjectLabelBO subjectLabelBO=SubjectLabelDTOConverter.INSTANCE.convertDtoToLabelBO(subjectLabelDTO);
            List<SubjectLabelBO> subjectLabelBOList = subjectLabelDomainService.queryByCategoryId(subjectLabelBO);
            List<SubjectLabelDTO> resultList=SubjectLabelDTOConverter.INSTANCE.convertBoToLabelDTOList(subjectLabelBOList);
            return Result.success(resultList);
        }catch (Exception e){
            log.error("SubjectCategoryController.update.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }
}
