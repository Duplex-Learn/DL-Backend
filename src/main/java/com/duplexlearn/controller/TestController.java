package com.duplexlearn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 临时用于测试的接口
 *
 * TODO 生产环境请删除
 *
 * @author LoveLonelyTime
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
