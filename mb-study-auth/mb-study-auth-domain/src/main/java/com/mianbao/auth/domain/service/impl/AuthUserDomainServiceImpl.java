package com.mianbao.auth.domain.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.mianbao.auth.common.enums.AuthUserStatusEnum;
import com.mianbao.auth.common.enums.IsDeletedFlagEnum;
import com.mianbao.auth.domain.constans.AuthConstant;
import com.mianbao.auth.domain.convert.AuthUserBOConverter;
import com.mianbao.auth.domain.entity.AuthUserBO;
import com.mianbao.auth.domain.redis.RedisUtil;
import com.mianbao.auth.domain.service.AuthUserDomainService;
import com.mianbao.auth.infra.basic.entity.*;
import com.mianbao.auth.infra.basic.service.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthUserDomainServiceImpl implements AuthUserDomainService {
    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private AuthRoleService authRoleService;

    private String salt = "bread";

    @Resource
    private RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    private static final String LOGIN_PREFIX = "loginCode";


    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        //校验用户是否存在
        AuthUser existAuthUser = new AuthUser();
        existAuthUser.setUserName(authUserBO.getUserName());
        List<AuthUser> existUser = authUserService.queryByCondition(existAuthUser);
        if (existUser.size() > 0) {
            return true;
        }
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        if (StringUtils.isNotBlank(authUser.getPassword())) {
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        }
        if (StringUtils.isBlank(authUser.getAvatar())) {
            authUser.setAvatar("http://43.143.4.9:9000/user/icon/微信图片_20231203153718(1).png");
        }
        if (StringUtils.isBlank(authUser.getNickName())) {
            authUser.setNickName("mianbao");
        }
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authUserService.insert(authUser);

        //建立一个初步的角色的关联
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        AuthRole roleResult = authRoleService.queryByCondition(authRole);
        Long roleId = roleResult.getId();
        Long userId = authUser.getId();
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);
        authUserRole.setRoleId(roleId);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUserRoleService.insert(authUserRole);

        String roleKey = redisUtil.buildKey(authRolePrefix, authUser.getUserName());
        List<AuthRole> roleList = new LinkedList<>();
        roleList.add(authRole);
        redisUtil.set(roleKey, new Gson().toJson(roleList));

        AuthRolePermission authRolePermission = new AuthRolePermission();
        authRolePermission.setRoleId(roleId);
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.
                queryByCondition(authRolePermission);

        List<Long> permissionIdList = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId).collect(Collectors.toList());
        //根据roleId查权限
        List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);
        //auth.permission
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, authUser.getUserName());
        redisUtil.set(permissionKey, new Gson().toJson(permissionList));

        return count > 0;
    }


    @Override
    public Boolean update(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        Integer count = authUserService.updateByUserName(authUser);
        return count > 0;
    }

    @Override
    public SaTokenInfo doLogin(String validCode) {
        String loginKey = redisUtil.buildKey(LOGIN_PREFIX, validCode);
        String openId = redisUtil.get(loginKey);

        if (StringUtils.isBlank(openId)) {
            return null;
        }

        // 创建一个AuthUserBO，包含用户的用户名
        AuthUserBO authUserBO = new AuthUserBO();
        authUserBO.setUserName(openId);
        System.out.println(openId + " 这是openid");

        // 调用register方法注册用户并初始化用户信息，包括权限
        this.register(authUserBO);

        // 登录操作，使用 openId 作为登录标识
        StpUtil.login(openId);

        // 获取当前用户的权限，并将其存储到Redis中

            // 查询该用户的角色和权限信息
            AuthRole authRole = new AuthRole();
            authRole.setRoleKey(AuthConstant.NORMAL_USER); // 假设角色是普通用户
            AuthRole roleResult = authRoleService.queryByCondition(authRole);
            Long roleId = roleResult.getId();

            // 查询角色的权限
            AuthRolePermission authRolePermission = new AuthRolePermission();
            authRolePermission.setRoleId(roleId);
            List<AuthRolePermission> rolePermissionList = authRolePermissionService.queryByCondition(authRolePermission);

            List<Long> permissionIdList = rolePermissionList.stream()
                    .map(AuthRolePermission::getPermissionId).collect(Collectors.toList());

            // 根据权限ID查找具体的权限
            List<AuthPermission> permissionList = authPermissionService.queryByRoleList(permissionIdList);

            // 将权限信息存入Redis
            String permissionKey = redisUtil.buildKey(authPermissionPrefix, openId);
            redisUtil.set(permissionKey, new Gson().toJson(permissionList));

            // 角色信息存入Redis
            String roleKey = redisUtil.buildKey(authRolePrefix, openId);
            redisUtil.set(roleKey, new Gson().toJson(roleResult));


        // 获取并返回 Token 信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo;
    }


    @Override
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setUserName(authUserBO.getUserName());
        List<AuthUser> userList = authUserService.queryByCondition(authUser);
        if (CollectionUtils.isEmpty(userList)) {
            return new AuthUserBO();
        }
        AuthUser user = userList.get(0);
        return AuthUserBOConverter.INSTANCE.convertEntityToBO(user);
    }

    @Override
    public List<AuthUserBO> listUserInfoByIds(List<String> userNameList) {
        List<AuthUser> userList = authUserService.listUserInfoByIds(userNameList);
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }
        return AuthUserBOConverter.INSTANCE.convertEntityToBO(userList);
    }
}
