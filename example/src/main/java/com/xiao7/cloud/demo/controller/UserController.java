package com.xiao7.cloud.demo.controller;

import com.xiao7.cloud.demo.entity.user.User;
import com.xiao7.cloud.demo.service.user.ElasticRepository;
import com.xiao7.cloud.demo.service.user.IUserRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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

  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private ElasticRepository elasticRepository;


  @GetMapping("detailByEs/{id}")
  public Page<User> detailByEs(@PathVariable("id") String id) {
    NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
    searchQueryBuilder
            .withPageable(Pageable.unpaged())
            .withQuery(QueryBuilders.matchPhraseQuery("content", id))
            .withHighlightFields(
                    new HighlightBuilder.Field("content")
                            .preTags("<em>")
                            .postTags("</em>"));
    return elasticRepository.search(searchQueryBuilder.build());
//    return elasticRepository.findByContent(id, Pageable.unpaged());
  }

  @GetMapping("detail/{id}")
  public User detail(@PathVariable("id") String id) {
    return userRepository.getByIdInBill(id);
  }

  @PostMapping("submit")
  public boolean save(@RequestBody User user) {
    userRepository.save(user);
    elasticRepository.save(user);
    return true;
  }

  @PutMapping("update")
  public boolean update(@RequestBody User user) {
    elasticRepository.save(user);
    return userRepository.updateById(user);
  }

  @DeleteMapping("delete")
  public boolean delete(String ids) {
    return userRepository.removeByIds(Arrays.asList(ids.split(",")));
  }

  @DeleteMapping("delete/{id}")
  public boolean deleteById(@PathVariable("id") String id) {
    return userRepository.deleteByIdInBill(id);
  }
}
