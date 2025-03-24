package com.mianbao.practice.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller启动问题
 *
 * @author: bread
 * @date: 2024/12/25
 */
@RestController
@RequestMapping("/practice/")
@Slf4j
@CrossOrigin("*")

public class DemoController {

    @RequestMapping("test")
    public String test() {
        return "test";
    }

}
