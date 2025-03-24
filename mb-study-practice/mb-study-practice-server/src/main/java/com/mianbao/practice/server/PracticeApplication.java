package com.mianbao.practice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 练习微服务启动类
 *
 * @author: bread
 * @date: 2024/12/25
 */
@SpringBootApplication
@ComponentScan("com.mianbao")
@MapperScan("com.mianbao.**.dao")
@EnableFeignClients(basePackages = "com.mianbao")
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class);
    }

}
