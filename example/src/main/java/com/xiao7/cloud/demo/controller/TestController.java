package com.xiao7.cloud.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：xiao7
 * @date ：Created in 2020/5/28 23:34
 * @description：测试控制器
 */
@RestController
public class TestController {

  @GetMapping("/test")
  public String test() {
    return "测试";
  }
}
