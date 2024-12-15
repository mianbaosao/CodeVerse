package com.mianbao.subject.application.convert;

import com.mianbao.subject.application.dto.SubjectAnswerDTO;
import com.mianbao.subject.domain.entity.SubjectAnswerBO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T16:43:14+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class SubjectAnswerDTOConverterImpl implements SubjectAnswerDTOConverter {

    @Override
    public List<SubjectAnswerDTO> convertBoToLabelDTOList(List<SubjectAnswerBO> subjectAnswerBOS) {
        if ( subjectAnswerBOS == null ) {
            return null;
        }

        List<SubjectAnswerDTO> list = new ArrayList<SubjectAnswerDTO>( subjectAnswerBOS.size() );
        for ( SubjectAnswerBO subjectAnswerBO : subjectAnswerBOS ) {
            list.add( convertBoToInfoDTO( subjectAnswerBO ) );
        }

        return list;
    }

    @Override
    public SubjectAnswerBO convertDtoToInfoBO(SubjectAnswerDTO subjectAnswerDTO) {
        if ( subjectAnswerDTO == null ) {
            return null;
        }

        SubjectAnswerBO subjectAnswerBO = new SubjectAnswerBO();

        subjectAnswerBO.setOptionType( subjectAnswerDTO.getOptionType() );
        subjectAnswerBO.setOptionContent( subjectAnswerDTO.getOptionContent() );
        subjectAnswerBO.setIsCorrect( subjectAnswerDTO.getIsCorrect() );

        return subjectAnswerBO;
    }

    @Override
    public SubjectAnswerDTO convertBoToInfoDTO(SubjectAnswerBO subjectAnswerBO) {
        if ( subjectAnswerBO == null ) {
            return null;
        }

        SubjectAnswerDTO subjectAnswerDTO = new SubjectAnswerDTO();

        subjectAnswerDTO.setOptionType( subjectAnswerBO.getOptionType() );
        subjectAnswerDTO.setOptionContent( subjectAnswerBO.getOptionContent() );
        subjectAnswerDTO.setIsCorrect( subjectAnswerBO.getIsCorrect() );

        return subjectAnswerDTO;
    }

    @Override
    public List<SubjectAnswerBO> convertDTOToBOList(List<SubjectAnswerDTO> subjectAnswerDTOS) {
        if ( subjectAnswerDTOS == null ) {
            return null;
        }

        List<SubjectAnswerBO> list = new ArrayList<SubjectAnswerBO>( subjectAnswerDTOS.size() );
        for ( SubjectAnswerDTO subjectAnswerDTO : subjectAnswerDTOS ) {
            list.add( convertDtoToInfoBO( subjectAnswerDTO ) );
        }

        return list;
    }
}
