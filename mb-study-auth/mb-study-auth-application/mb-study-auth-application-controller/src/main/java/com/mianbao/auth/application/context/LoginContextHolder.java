package com.mianbao.auth.application.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录上下文对象
 *
 * @author: bread
 * @date: 2024/09/18
 */
public class LoginContextHolder {

    /**
     * 这段代码实现了一个基于 ThreadLocal 的登录上下文管理器 LoginContextHolder。其
     * 目的是将用户登录相关的信息（如用户 ID 等）保存在当前线程的上下文中，从而在整个请求链路中共享这些信息，
     * 适合在没有全局 Session 或需要线程安全的环境下使用。以下是详细的功能说明：
     */
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL
            = new InheritableThreadLocal<>();

    public static void set(String key, Object val) {
        Map<String, Object> map = getThreadLocalMap();
        map.put(key, val);
    }

    public static Object get(String key){
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.get(key);
    }

    public static String getLoginId(){
        return (String) getThreadLocalMap().get("loginId");
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }

    public static Map<String, Object> getThreadLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }


}
