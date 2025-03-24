package com.mianbao.subject.infrastructure.basic.entity.entity;

import java.io.Serializable;

/**
 * (SubjectCode)实体类
 *
 * @author makejava
 * @since 2025-01-11 21:37:37
 */
public class SubjectCode implements Serializable {
    private static final long serialVersionUID = 484630454756378146L;

    private Integer id;

    private Integer subjectId;

    private String subjectDesc;

    private String nums;

    private String result;

    private String hints;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

}

