package com.mianbao.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (AuthRole)实体类
 *
 * @author makejava
 * @since 2024-09-18 16:23:23
 */
@Data
public class AuthRole implements Serializable {
    
    private Long id;
    
    private String roleName;
    
    private String roleKey;
    
    private String createdBy;
    
    private Date createdTime;
    
    private String updateBy;
    
    private Date updateTime;
    
    private Integer isDeleted;

}

