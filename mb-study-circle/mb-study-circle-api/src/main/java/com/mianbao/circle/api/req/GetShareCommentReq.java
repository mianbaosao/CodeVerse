package com.mianbao.circle.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * bao圈内容信息
 * </p>
 *
 * @author: bread
 * @date: 2025/1/25
 */
@Getter
@Setter
public class GetShareCommentReq implements Serializable {

    private Long id;

}
