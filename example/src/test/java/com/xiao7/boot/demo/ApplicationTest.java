package com.xiao7.boot.demo;

import com.xiao7.cloud.demo.SpringbootDemoApplication;
import com.xiao7.cloud.demo.entity.user.User;
import com.xiao7.cloud.demo.service.user.ElasticRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author ：xiao7
 * @date ：Created in 2020/5/29 22:44
 * @description：测试
 */
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class ApplicationTest {

    @Autowired
    ElasticRepository elasticRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testES() {
        User user = new User();
        user.setAge(12);
        user.setContent("asd");
        user.setUserName("asd");
        user.setId("12412412");
        System.out.println(elasticRepository.queryById("12412412"));
    }

    @Test
    public void testRedis() {
        User user = new User();
        user.setAge(12);
        user.setContent("asd");
        user.setUserName("asd");
        user.setId("12412412");
        redisTemplate.opsForValue().set("user", user);
        redisTemplate.opsForValue().set("asd", "大师法士大夫不是的");
        redisTemplate.opsForValue().set("123", "asfasfasfasf");
        System.out.println(redisTemplate.opsForValue().get("asd").equals("大师法士大夫不是的"));
        System.out.println(redisTemplate.opsForValue().get("123").equals("asfasfasfasf"));
    }

}
