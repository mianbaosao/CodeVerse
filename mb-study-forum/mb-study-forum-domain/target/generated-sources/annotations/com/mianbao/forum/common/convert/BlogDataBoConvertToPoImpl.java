package com.mianbao.forum.common.convert;

import com.mianbao.forum.common.entity.BlogDataBO;
import com.mianbao.forum.infa.basic.entity.ForumBlogdata;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T19:56:05+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class BlogDataBoConvertToPoImpl implements BlogDataBoConvertToPo {

    @Override
    public ForumBlogdata BlogDataTOPo(BlogDataBO blogDataBO) {
        if ( blogDataBO == null ) {
            return null;
        }

        ForumBlogdata forumBlogdata = new ForumBlogdata();

        forumBlogdata.setBlogid( blogDataBO.getBlogid() );
        forumBlogdata.setCover( blogDataBO.getCover() );
        forumBlogdata.setTitle( blogDataBO.getTitle() );
        forumBlogdata.setWriter( blogDataBO.getWriter() );
        forumBlogdata.setCategoryname( blogDataBO.getCategoryname() );
        forumBlogdata.setAllowcomment( blogDataBO.getAllowcomment() );
        forumBlogdata.setStatusname( blogDataBO.getStatusname() );
        forumBlogdata.setContent( blogDataBO.getContent() );
        forumBlogdata.setDescription( blogDataBO.getDescription() );
        forumBlogdata.setCategoryid( blogDataBO.getCategoryid() );
        forumBlogdata.setTime( blogDataBO.getTime() );
        forumBlogdata.setBrowse( blogDataBO.getBrowse() );
        forumBlogdata.setLikes( blogDataBO.getLikes() );

        return forumBlogdata;
    }
}
