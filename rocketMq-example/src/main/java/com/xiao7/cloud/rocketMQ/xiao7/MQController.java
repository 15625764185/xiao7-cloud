package com.xiao7.cloud.rocketMQ.xiao7;


import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户表 前端控制器
 *
 * @author xiao7
 * @since 2020-05-29
 */
@RestController
@RequestMapping("/mq")
public class MQController {

    @Autowired
    private Producer producer;

    @GetMapping("/send/{message}")
    public boolean delete(@PathVariable("message") String message) {
        JSON.parse(message.getBytes());
        Message msg = new Message("base", "tagA", message.getBytes());
        producer.syncSend(msg);
        return true;
    }


}
