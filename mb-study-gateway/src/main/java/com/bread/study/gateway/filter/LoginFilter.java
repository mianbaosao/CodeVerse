package com.bread.study.gateway.filter;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 登录拦截器
 */
@Component
@Slf4j
public class LoginFilter implements GlobalFilter {

    @Resource
    private RedisTemplate<String, String> redisTemplate; // 注入RedisTemplate

    private static final String LOCK_KEY_PREFIX = "lock:login:"; // 分布式锁的前缀

    @Override
    @SneakyThrows
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();
        log.info("LoginFilter.filter.url:{}", url);
        if (url.equals("/user/doLogin")) {
            return chain.filter(exchange);
        }


        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        String loginId = (String) tokenInfo.getLoginId();
        String lockKey = LOCK_KEY_PREFIX + loginId; // 创建锁的唯一键
        mutate.header("loginId", loginId);
        // 尝试获取分布式锁
        Boolean isLockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey, "lock", 10, TimeUnit.SECONDS);
        log.info(String.valueOf(isLockAcquired)+"这是分布式锁的获取是否");
        if (Boolean.TRUE.equals(isLockAcquired)) {
            try {
                log.info("Lock acquired for loginId: {}", loginId);
                // 将提取到的 loginId 添加到请求头中
                mutate.header("loginId", loginId);
                return chain.filter(exchange.mutate().request(mutate.build()).build());
            } finally {
                // 释放锁
                redisTemplate.delete(lockKey);
                log.info("Lock released for loginId: {}", loginId);
            }
        } else {
            log.warn("Failed to acquire lock for loginId: {}, retry later.", loginId);
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return response.setComplete();
        }
    }
}

