package com.xiao7.cloud.demo.controller;

import com.xiao7.cloud.demo.entity.user.User;
import com.xiao7.cloud.demo.service.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 用户表 前端控制器
 *
 * @author xiao7
 * @since 2020-05-29
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private IUserRepository userRepository;

  @GetMapping("detail/{id}")
  public User detail(@PathVariable("id") String id) {
    return userRepository.getById(id);
  }

  @PostMapping("submit")
  public boolean save(@RequestBody User user) {
    return userRepository.save(user);
  }

  @PutMapping("update")
  public boolean update(@RequestBody User user) {
    return userRepository.updateById(user);
  }

  @DeleteMapping("delete")
  public boolean delete(String ids) {
    return userRepository.removeByIds(Arrays.asList(ids.split(",")));
  }
}
