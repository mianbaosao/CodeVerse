package com.mianbao.auth.application.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mianbao.auth.application.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * mvc的全局处理
 *
 * @author: bread
 * @date: 2024/09/18
 */
@Configuration
public class GlobalConfig extends WebMvcConfigurationSupport {

    /**
     * 配置消息转换器，添加自定义的 MappingJackson2HttpMessageConverter
     * 使 JSON 序列化忽略 null 字段
     * @param converters Spring 提供的 HTTP 消息转换器列表
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(mappingJackson2HttpMessageConverter());
    }

    /**
     * 配置拦截器，将自定义的 LoginInterceptor 注册到 Spring MVC 拦截器链中
     * 设置拦截所有路径，但排除 "/user/doLogin"
     * @param registry 拦截器注册器，用于添加自定义拦截器
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/user/doLogin");
    }

    /**
     * 自定义mappingJackson2HttpMessageConverter
     * 目前实现：空值忽略，空字段可返回
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }


}
