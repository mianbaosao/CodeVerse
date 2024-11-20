package com.mianbao.subject.domain.convert;

import com.mianbao.subject.domain.entity.SubjectInfoBO;
import com.mianbao.subject.infrastructure.basic.entity.SubjectBrief;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-18T19:49:10+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class BriefSubjectConverterImpl implements BriefSubjectConverter {

    @Override
    public SubjectBrief convertBoToEntity(SubjectInfoBO subjectInfoBO) {
        if ( subjectInfoBO == null ) {
            return null;
        }

        SubjectBrief subjectBrief = new SubjectBrief();

        subjectBrief.setId( subjectInfoBO.getId() );
        subjectBrief.setSubjectAnswer( subjectInfoBO.getSubjectAnswer() );

        return subjectBrief;
    }
}
