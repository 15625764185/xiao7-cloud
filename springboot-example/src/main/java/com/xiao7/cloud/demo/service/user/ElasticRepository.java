package com.xiao7.cloud.demo.service.user;

import com.xiao7.cloud.demo.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author ：xiao7
 * @date ：Created in 2020/6/7 20:25
 * @description：ES仓储层
 */
public interface ElasticRepository extends ElasticsearchRepository<User, String> {
    Page<User> findByContent(String content, Pageable pageable);

    User queryById(String id);
}
