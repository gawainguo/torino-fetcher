package com.jiaqi.torino.fetcher.handler.mq;

import com.jiaqi.torino.fetcher.model.mq.MQMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerHandler implements ProducerHandler {

    @Autowired
    private KafkaTemplate<String, String> kafkaTempalte;

    @Override
    public Boolean produce(MQMessage msg) {
        kafkaTempalte.send(msg.getTopic(), msg.getHashKey(), msg.getMessage());
        return true;
    }
}