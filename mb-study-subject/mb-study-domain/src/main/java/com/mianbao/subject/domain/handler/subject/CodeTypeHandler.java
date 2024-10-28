package com.mianbao.subject.domain.handler.subject;

import com.mianbao.subject.common.enums.SubjectInfoTypeEnum;
import com.mianbao.subject.domain.entity.SubjectInfoBO;
import com.mianbao.subject.domain.entity.SubjectOptionBO;
import com.mianbao.subject.infrastructure.basic.service.SubjectInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

/**
 * @Description: 编码题
 * @Author:bread
 * @Date: 2024-10-28 19:01
 */
@Component
public class CodeTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectInfoService subjectInfoService;



    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.CODE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {

    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        return null;
    }
}
