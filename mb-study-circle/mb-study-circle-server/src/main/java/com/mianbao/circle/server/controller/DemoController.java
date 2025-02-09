package com.mianbao.circle.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller启动问题
 *
 * @author: bread
 * @date: 2025/1/25
 */
@RestController
@RequestMapping("/circle/")
@Slf4j
@CrossOrigin("*")

public class DemoController {

    @RequestMapping("test")
    public String test() {
        return "test";
    }

}
