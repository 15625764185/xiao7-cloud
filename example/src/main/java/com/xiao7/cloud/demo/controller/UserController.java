package com.xiao7.cloud.demo.controller;

import com.xiao7.cloud.demo.entity.user.EsResultMapper;
import com.xiao7.cloud.demo.entity.user.User;
import com.xiao7.cloud.demo.service.user.ElasticRepository;
import com.xiao7.cloud.demo.service.user.IUserRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
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
  @Autowired private ElasticRepository elasticRepository;

  @Autowired private ElasticsearchRestTemplate elasticsearchTemplate;

  @Autowired private EsResultMapper esResultMapper;

  @Autowired private RedisTemplate redisTemplate;

  @GetMapping("detailByEs/{id}")
  public Page<User> detailByEs(@PathVariable("id") String id) {
    NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
    HighlightBuilder highlightBuilder = new HighlightBuilder();
    highlightBuilder.field(new HighlightBuilder.Field("content"));
    searchQueryBuilder
        .withPageable(Pageable.unpaged())
        .withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("content", id)))
        .withFilter(QueryBuilders.rangeQuery("age").gt(11))
        .withHighlightBuilder(new HighlightBuilder().field("content"));

    return elasticsearchTemplate.queryForPage(
        searchQueryBuilder.build(), User.class, esResultMapper);
    // return elasticRepository.search(searchQueryBuilder.build());
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

  @GetMapping("testRedis/{id}")
  public boolean testRedis(@PathVariable("id") String id) {
    redisTemplate.opsForValue().set("test", id);
    return true;
  }
}
