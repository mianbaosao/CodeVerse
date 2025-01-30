package com.mianbao.circle.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mianbao.circle.server.dao.SensitiveWordsMapper;
import com.mianbao.circle.server.entity.po.SensitiveWords;
import com.mianbao.circle.server.service.SensitiveWordsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 敏感词表 服务实现类
 * </p>
 *
 * @author: bread
 * @date: 2025/1/25
 */
@Service
public class SensitiveWordsServiceImpl extends ServiceImpl<SensitiveWordsMapper, SensitiveWords> implements SensitiveWordsService {

}
