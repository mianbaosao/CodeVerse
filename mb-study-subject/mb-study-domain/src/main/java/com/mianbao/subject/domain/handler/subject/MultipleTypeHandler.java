package com.mianbao.subject.domain.handler.subject;

import com.google.common.base.Preconditions;


import com.mianbao.subject.common.enums.SubjectInfoTypeEnum;
import com.mianbao.subject.domain.entity.SubjectAnswerBO;
import com.mianbao.subject.domain.convert.MultipleSubejectConverter;
import com.mianbao.subject.domain.entity.SubjectInfoBO;
import com.mianbao.subject.domain.entity.SubjectOptionBO;

import com.mianbao.subject.infrastructure.basic.entity.SubjectBrief;
import com.mianbao.subject.infrastructure.basic.entity.SubjectMultiple;
import com.mianbao.subject.infrastructure.basic.service.SubjectMultipleService;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 多选策略类
 * @Author:bread
 * @Date: 2024-08-26 17:34
 */
@Component
public class MultipleTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectMultipleService subjectMultipleService;
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //多选
        List<SubjectMultiple> subjectMultipleList=new LinkedList<>();
        Preconditions.checkNotNull(!CollectionUtils.isEmpty(subjectInfoBO.getOptionList()),"列表不能为空");

        subjectInfoBO.getOptionList().forEach(option->{
            SubjectMultiple subjectMultiple = MultipleSubejectConverter.INSTANCE.converAnswerBoToJudge(option);
            subjectMultiple.setSubjectId(subjectInfoBO.getId());
            subjectMultipleList.add(subjectMultiple);
        });
        subjectMultipleService.batchInsert(subjectMultipleList);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectMultiple subjectMultiple = new SubjectMultiple();
        subjectMultiple.setSubjectId(Long.valueOf(subjectId));
        List<SubjectMultiple> result = subjectMultipleService.queryByCondition(subjectMultiple);
        List<SubjectAnswerBO> subjectAnswerBOList = MultipleSubejectConverter.INSTANCE.convertEntityToBoList(result);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }
}
