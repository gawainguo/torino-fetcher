package com.jiaqi.torino.fetcher.service.message;

import com.jiaqi.torino.fetcher.handler.mq.KafkaProducerHandler;
import com.jiaqi.torino.fetcher.model.mq.MQMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaTestMessageService implements TestMessageService {

    @Autowired
    private KafkaProducerHandler producerHandler;

    @Value("${app.mq.test-topic}")
    private String newsTopic;

    @Override
    public MQMessage produceTestMessage(String msg) {
        MQMessage message = new MQMessage();
        String content = msg;
        String hashKey = msg;
        message.setMessage(content);
        message.setHashKey(hashKey);
        message.setTopic(newsTopic);
        message.setSuccess(producerHandler.produce(message));
        return message;
    }
    
}