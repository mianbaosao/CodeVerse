package com.mianbao.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.mianbao.subject.application.convert.SubjectAnswerDTOConverter;
import com.mianbao.subject.application.convert.SubjectInfoDTOConverter;
import com.mianbao.subject.application.dto.SubjectInfoDTO;

import com.mianbao.subject.domain.entity.SubjectAnswerBO;
import com.mianbao.subject.domain.entity.SubjectInfoBO;
import com.mianbao.subject.domain.service.SubjectInfoDomainService;
import com.qihang.subject.common.entity.PageResult;
import com.qihang.subject.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 题目控制层
 * @Author:bread
 * @Date: 2024-08-26 16:12
 */
@RestController
@RequestMapping("/subject")
@Slf4j
public class SubjectController {

    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;
    /**
     * 新增题目
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            // StringUtils.isBlank来检查字符串是否为空白，StringUtils.isBlank 会返回 true 如果字符串为 null
            Preconditions.checkArgument(!StringUtils.isBlank(subjectInfoDTO.getSubjectName()),
            "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectDifficult(), "题目难度不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(), "题目类型不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectScore(), "题目分数不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getCategoryIds())
                    , "分类id不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getLabelIds())
                    , "标签id不能为空");

            SubjectInfoBO subjectInfoBO= SubjectInfoDTOConverter.INSTANCE.convertDtoToInfoBO(subjectInfoDTO);
            List<SubjectAnswerBO> subjectAnswerBOS= SubjectAnswerDTOConverter.
                    INSTANCE.convertDTOToBOList(subjectInfoDTO.getOptionList());
            subjectInfoBO.setOptionList(subjectAnswerBOS);
           subjectInfoDomainService.add(subjectInfoBO);
            return Result.success(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增标签失败");
        }
    }


    /**
     * 查询题目列表
     */
    @PostMapping("/getSubjectPage")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPage.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            // StringUtils.isBlank来检查字符串是否为空白，StringUtils.isBlank 会返回 true 如果字符串为 null
            Preconditions.checkNotNull(!CollectionUtils.isEmpty(subjectInfoDTO.getCategoryIds()),"分类id不能为空");
            Preconditions.checkNotNull(!CollectionUtils.isEmpty(subjectInfoDTO.getLabelIds()),"标签id不能为空");
            SubjectInfoBO subjectInfoBO= SubjectInfoDTOConverter.INSTANCE.convertDtoToInfoBO(subjectInfoDTO);
          PageResult<SubjectInfoBO> boPageResult= subjectInfoDomainService.getSubjectPage(subjectInfoBO);
            return Result.success(boPageResult);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("查询分页失败");
        }
    }
    /**
     * 查询题目信息
     */
    @PostMapping("/querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.querySubjectInfo.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkNotNull(subjectInfoDTO.getId(), "题目id不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDtoToInfoBO(subjectInfoDTO);
            SubjectInfoBO boResult = subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
            SubjectInfoDTO dto = SubjectInfoDTOConverter.INSTANCE.convertBoToInfoDTO(boResult);
            return Result.success(dto);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("查询题目详情失败");
        }
    }

}