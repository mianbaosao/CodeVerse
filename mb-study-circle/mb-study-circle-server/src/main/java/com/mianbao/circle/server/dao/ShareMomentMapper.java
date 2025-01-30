package com.mianbao.circle.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mianbao.circle.server.entity.po.ShareMoment;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 动态信息 Mapper 接口
 * </p>
 *
 * @author: bread
 * @date: 2025/1/25
 */
public interface ShareMomentMapper extends BaseMapper<ShareMoment> {

    void incrReplyCount(@Param("id") Long id, @Param("count") int count);

}
