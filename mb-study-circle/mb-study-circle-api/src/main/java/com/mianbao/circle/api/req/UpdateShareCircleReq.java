package com.mianbao.circle.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 圈子信息
 * </p>
 *
 * @author: bread
 * @date: 2025/1/25
 */
@Getter
@Setter
public class UpdateShareCircleReq implements Serializable {

    private Long id;

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 圈子图标
     */
    private String icon;

    /**
     * -1为一级,分类ID
     */
    private Long parentId;

}
