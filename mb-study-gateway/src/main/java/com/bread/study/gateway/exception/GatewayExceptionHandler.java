package com.bread.study.gateway.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import com.bread.study.gateway.entity.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局异常
 */
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();

        // 默认的错误码和消息
        Integer code = 200;
        String message = "";

        if (throwable instanceof SaTokenException) {
            code = 401;
            message = "用户无权限";
            throwable.printStackTrace();
        } else if (throwable instanceof NotLoginException) {
            // 处理 NotLoginException 异常
            NotLoginException nle = (NotLoginException) throwable;
            code = 401;
            message = getMessageForNotLoginException(nle);
            throwable.printStackTrace();
        } else {
            code = 500;
            message = "系统繁忙";
            throwable.printStackTrace();
        }

        Result result = Result.fail(code, message);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            byte[] bytes = null;
            try {
                bytes = objectMapper.writeValueAsBytes(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return dataBufferFactory.wrap(bytes);
        }));
    }

    // 根据 NotLoginException 的类型返回相应的消息
    private String getMessageForNotLoginException(NotLoginException nle) {
        switch (nle.getType()) {
            case NotLoginException.NOT_TOKEN:
                return "未提供token";
            case NotLoginException.INVALID_TOKEN:
                return "token无效";
            case NotLoginException.TOKEN_TIMEOUT:
                return "token已过期";
            case NotLoginException.BE_REPLACED:
                return "token已被顶下线";
            case NotLoginException.KICK_OUT:
                return "token已被踢下线";
            default:
                return "当前会话未登录";
        }
    }
}

