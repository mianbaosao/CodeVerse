package com.mianbao.subject.infrastructure.basic.dao;

import com.mianbao.subject.infrastructure.basic.entity.SubjectBrief;
import com.mianbao.subject.infrastructure.basic.entity.SubjectCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简答题(SubjectBrief)表数据库访问层
 *
 * @author makejava
 * @since 2024-09-03 17:49:41
 */
public interface SubjectCodeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectCode queryById(Long id);



}

