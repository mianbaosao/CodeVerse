package com.mianbao.practice.server.util;

import com.mianbao.practice.server.config.context.LoginContextHolder;

/**
 * 用户登录util
 *
 * @author: bread
 * @date: 2024/12/26
 */
public class LoginUtil {

    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }


}
