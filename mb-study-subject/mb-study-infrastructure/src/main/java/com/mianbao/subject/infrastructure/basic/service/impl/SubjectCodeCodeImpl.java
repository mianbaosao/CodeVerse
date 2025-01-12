package com.mianbao.subject.infrastructure.basic.service.impl;

import com.mianbao.subject.infrastructure.basic.dao.SubjectBriefDao;
import com.mianbao.subject.infrastructure.basic.dao.SubjectCodeDao;
import com.mianbao.subject.infrastructure.basic.entity.SubjectBrief;
import com.mianbao.subject.infrastructure.basic.entity.SubjectCode;
import com.mianbao.subject.infrastructure.basic.service.SubjectBriefService;
import com.mianbao.subject.infrastructure.basic.service.SubjectCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 简答题(SubjectBrief)表服务实现类
 *
 * @author makejava
 * @since 2024-09-03 17:49:41
 */
@Service("subjectCodeService")
public class SubjectCodeCodeImpl implements SubjectCodeService {
    @Resource
    private SubjectCodeDao subjectCodeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectCode queryById(Long id) {
        return subjectCodeDao.queryById(id);
    }




}
