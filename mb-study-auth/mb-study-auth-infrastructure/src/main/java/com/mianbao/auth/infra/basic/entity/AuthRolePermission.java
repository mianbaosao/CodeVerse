package com.mianbao.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 角色权限关联表(AuthRolePermission)实体类
 *
 * @author makejava
 * @since 2024-09-18 16:22:47
 */
@Data
public class AuthRolePermission implements Serializable {
    
    private Long id;
    
    private Long roleId;
    
    private Long permissionId;
    
    private String createdBy;
    
    private Date createdTime;
    
    private String updateBy;
    
    private Date updateTime;
    
    private Integer isDeleted;
}

