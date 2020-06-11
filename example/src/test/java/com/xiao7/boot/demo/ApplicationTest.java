package com.xiao7.boot.demo;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.xiao7.cloud.demo.SpringbootDemoApplication;
import com.xiao7.cloud.demo.entity.user.User;
import com.xiao7.cloud.demo.service.user.ElasticRepository;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;

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
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testES() {
        User user = new User();
        user.setAge(12);
        user.setContent("asd");
        user.setUserName("asd");
        user.setId("12412412");
        //
        // user.setFileData("QmFzZTY057yW56CB6K+05piOCuOAgOOAgEJhc2U2NOe8lueggeimgeaxguaKijPkuKo45L2N5a2X6IqC77yIMyo4PTI077yJ6L2s5YyW5Li6NOS4qjbkvY3nmoTlrZfoioLvvIg0KjY9MjTvvInvvIzkuYvlkI7lnKg25L2N55qE5YmN6Z2i6KGl5Lik5LiqMO+8jOW9ouaIkDjkvY3kuIDkuKrlrZfoioLnmoTlvaLlvI/jgIIg5aaC5p6c5Ymp5LiL55qE5a2X56ym5LiN6LazM+S4quWtl+iKgu+8jOWImeeUqDDloavlhYXvvIzovpPlh7rlrZfnrKbkvb/nlKgnPSfvvIzlm6DmraTnvJbnoIHlkI7ovpPlh7rnmoTmlofmnKzmnKvlsL7lj6/og73kvJrlh7rnjrAx5oiWMuS4qic9J+OAggoK44CA44CA5Li65LqG5L+d6K+B5omA6L6T5Ye655qE57yW56CB5L2N5Y+v6K+75a2X56ym77yMQmFzZTY05Yi25a6a5LqG5LiA5Liq57yW56CB6KGo77yM5Lul5L6/6L+b6KGM57uf5LiA6L2s5o2i44CC57yW56CB6KGo55qE5aSn5bCP5Li6Ml42PTY077yM6L+Z5Lmf5pivQmFzZTY05ZCN56ew55qE55Sx5p2l44CC");

        try {
            File file = new File("C:\\Users\\Administrator\\Desktop\\答辩分组名单---(1)(1)(1).xlsx");
            //            String str = StrUtil.str(Base64Encoder.encode(FileUtil.readBytes(file), false,
            // false), "UTF-8");
            user.setFileData(Base64.encode(file));
            IndexRequest indexRequest = new IndexRequest().setPipeline("attachment").index("example")
                    .type("user").id(user.getId()).source(JSON.toJSONString(user), XContentType.JSON);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //        User user1 = elasticRepository.queryById("12412412");
        // user1.getAttachment().setContent(user1.getAttachment().getContent().replaceAll("\r|\n", ""));
        //        System.out.println(user1);
        //        GetRequest indexRequest = new
        // GetRequest().index("example").type("user").id("12412412");
        //        try {
        //            GetResponse response = restHighLevelClient.get(indexRequest,
        // RequestOptions.DEFAULT);
        //            System.out.println(response);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
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
