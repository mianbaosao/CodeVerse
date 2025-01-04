package com.mianbao.subject.application.convert;

import com.mianbao.subject.application.dto.SubjectLabelDTO;
import com.mianbao.subject.domain.entity.SubjectLabelBO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-27T20:26:01+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class SubjectLabelDTOConverterImpl implements SubjectLabelDTOConverter {

    @Override
    public List<SubjectLabelDTO> convertBoToLabelDTOList(List<SubjectLabelBO> subjectLabelDTO) {
        if ( subjectLabelDTO == null ) {
            return null;
        }

        List<SubjectLabelDTO> list = new ArrayList<SubjectLabelDTO>( subjectLabelDTO.size() );
        for ( SubjectLabelBO subjectLabelBO : subjectLabelDTO ) {
            list.add( convertBoToLabelDTO( subjectLabelBO ) );
        }

        return list;
    }

    @Override
    public SubjectLabelBO convertDtoToLabelBO(SubjectLabelDTO subjectLabelDTO) {
        if ( subjectLabelDTO == null ) {
            return null;
        }

        SubjectLabelBO subjectLabelBO = new SubjectLabelBO();

        subjectLabelBO.setId( subjectLabelDTO.getId() );
        subjectLabelBO.setLabelName( subjectLabelDTO.getLabelName() );
        subjectLabelBO.setSortNum( subjectLabelDTO.getSortNum() );
        subjectLabelBO.setCategoryId( subjectLabelDTO.getCategoryId() );

        return subjectLabelBO;
    }

    @Override
    public SubjectLabelDTO convertBoToLabelDTO(SubjectLabelBO subjectLabelBO) {
        if ( subjectLabelBO == null ) {
            return null;
        }

        SubjectLabelDTO subjectLabelDTO = new SubjectLabelDTO();

        subjectLabelDTO.setId( subjectLabelBO.getId() );
        subjectLabelDTO.setLabelName( subjectLabelBO.getLabelName() );
        subjectLabelDTO.setCategoryId( subjectLabelBO.getCategoryId() );
        subjectLabelDTO.setSortNum( subjectLabelBO.getSortNum() );

        return subjectLabelDTO;
    }

    @Override
    public List<SubjectLabelBO> convertDTOToBOList(List<SubjectLabelDTO> subjectLabelDTOList) {
        if ( subjectLabelDTOList == null ) {
            return null;
        }

        List<SubjectLabelBO> list = new ArrayList<SubjectLabelBO>( subjectLabelDTOList.size() );
        for ( SubjectLabelDTO subjectLabelDTO : subjectLabelDTOList ) {
            list.add( convertDtoToLabelBO( subjectLabelDTO ) );
        }

        return list;
    }
}
