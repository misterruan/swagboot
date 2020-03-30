package com.rock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rock on 2019/11/5.
 * kafka测试controller
 */
@RestController
public class KafKaController {


    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;




    /**
     * 生产者发送kafka消息
     * @param message
     * @return
     */
    @GetMapping("/message/send")
    public boolean send(@RequestParam String message){
        kafkaTemplate.send("testTopic",message);
        return true;
    }


}
