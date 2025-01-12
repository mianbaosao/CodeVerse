package com.mianbao.subject.infrastructure.basic.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

/**
 * @Description: 编程题详细
 * @Author:bread
 * @Date: 2025-01-11 21:38
 */
@Data
public class SubjectCode {
    private Long id;
    private Long subjectId;
    private String subjectDesc;

    private List<List<Integer>> nums;

    private List<List<Integer>> result;

    private String hints;

}
