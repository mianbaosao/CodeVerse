package com.mianbao.practice.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpecialPracticeLabelVO implements Serializable {

    private Long id;

    /**
     * 分类id-标签ID
     */
    private String assembleId;

    private String labelName;

}
