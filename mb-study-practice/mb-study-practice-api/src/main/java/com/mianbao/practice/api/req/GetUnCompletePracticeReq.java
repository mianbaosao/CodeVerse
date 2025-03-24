package com.mianbao.practice.api.req;

import com.mianbao.practice.api.common.PageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetUnCompletePracticeReq implements Serializable {

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

}