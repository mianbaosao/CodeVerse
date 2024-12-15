package com.mianbao.forum.application.convert;

import com.mianbao.forum.application.dto.BlogDataDTO;
import com.mianbao.forum.common.entity.BlogDataBO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-07T16:43:10+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class BlogDataDtoConverToBoImpl implements BlogDataDtoConverToBo {

    @Override
    public BlogDataBO converBlogDataDtoToBo(BlogDataDTO blogDataDTO) {
        if ( blogDataDTO == null ) {
            return null;
        }

        BlogDataBO blogDataBO = new BlogDataBO();

        blogDataBO.setBlogid( blogDataDTO.getBlogid() );
        blogDataBO.setCategoryid( blogDataDTO.getCategoryid() );

        return blogDataBO;
    }
}
