package com.mianbao.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目dto
 *
 * @author: bread
 * @date: 2024/9/5
 */
@Data
public class SubjectOptionBO implements Serializable {

    /**
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 答案选项
     */
    private List<SubjectAnswerBO> optionList;

    /**
     * 题目描述
     */
    private String subjectDesc;

    /**
     * 输入
     */
    private List<List<Integer>>nums;

    /**
     * 输出
     */
    private List<List<Integer>>result;

    /**
     * 提示
     */
    private String hints;
}

