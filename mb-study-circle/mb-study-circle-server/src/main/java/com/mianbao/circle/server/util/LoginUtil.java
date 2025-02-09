package com.mianbao.circle.server.util;

import com.mianbao.circle.server.config.context.LoginContextHolder;

/**
 * 用户登录util
 *
 * @author: bread
 * @date: 2024/11/26
 */
public class LoginUtil {

    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }


}
