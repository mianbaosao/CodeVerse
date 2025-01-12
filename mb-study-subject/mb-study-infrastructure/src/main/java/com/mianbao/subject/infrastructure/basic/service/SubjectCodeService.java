package com.mianbao.subject.infrastructure.basic.service;

import com.mianbao.subject.infrastructure.basic.entity.SubjectBrief;
import com.mianbao.subject.infrastructure.basic.entity.SubjectCode;

/**
 * 简答题(SubjectBrief)表服务接口
 *
 * @author makejava
 * @since 2024-09-03 17:49:41
 */
public interface SubjectCodeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectCode queryById(Long id);


}
