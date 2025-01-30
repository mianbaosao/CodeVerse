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
public class RemoveShareCircleReq implements Serializable {

    private Long id;

}
