package com.mianbao.forum.common.handler.recommend;

import com.mianbao.forum.common.entity.BlogDataBO;
import com.study.forum.common.enums.RecommendAlgorithm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @Description: 协同过滤推荐算法
 * @Author:bread
 * @Date: 2024-10-18 20:32
 */
@Component
public class CFAlgorithm implements RecommendType{
    @Override
    public RecommendAlgorithm getType() {
        return RecommendAlgorithm.CONTENT_BASED;
    }

    @Override
    public BlogDataBO query(int userId) {
        // 获取用户的点赞和收藏记录


        // 随机选取 20 个其他用户


        // 获取其他用户的点赞和收藏记录


        // 计算 Jaccard 相似系数


        // 获取相似用户点赞/收藏的内容，并过滤掉当前用户已操作的内容


        // 如果推荐的内容不够，继续查找下一个相似用户


        // 返回推荐结果，转换为 BlogDataBO

        return null;
    }
}
