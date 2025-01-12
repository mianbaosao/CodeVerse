package com.mianbao.subject.domain.service;


import com.mianbao.subject.common.entity.PageResult;
import com.mianbao.subject.domain.entity.SubjectInfoBO;
import com.mianbao.subject.infrastructure.basic.entity.SubjectInfoEs;

import java.util.List;


/**
 * @Description: 题目信息
 * @Author:bread
 * @Date: 2024-08-26 17:09
 */
public interface SubjectInfoDomainService {
    /**
     * 新增题目
     * @param subjectInfoBO
     * @return
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 分页查询
     * @param subjectInfoBO
     * @return
     */
    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);


    /**
     * 查询编程题
     * @param subjectInfoBO
     * @return
     */
    PageResult<SubjectInfoBO> getCodeSubjectPage(SubjectInfoBO subjectInfoBO);

    /**
     * 查看题目信息
     * @param subjectInfoBO
     * @return
     */
    SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO);

    /**
     * 全文检索
     */
    PageResult<SubjectInfoEs> getSubjectPageBySearch(SubjectInfoBO subjectInfoBO);
    /**
     * 获取贡献榜
     */
    List<SubjectInfoBO> getContributeList();

}
