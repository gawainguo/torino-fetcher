package com.jiaqi.torino.fetcher.service.message;

import com.google.gson.Gson;
import com.jiaqi.torino.fetcher.handler.mq.KafkaProducerHandler;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;
import com.jiaqi.torino.fetcher.model.mq.MQMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaNewsMessageService implements NewsMessageService {

    @Autowired
    private KafkaProducerHandler producerHandler;

    @Value("${app.mq.news-topic}")
    private String newsTopic;

    @Override
    public MQMessage produceNewsMessage(NewsArticle news) {
        MQMessage message = new MQMessage();
        String content = new Gson().toJson(news);
        String hashKey = news.getUrl();
        message.setMessage(content);
        message.setHashKey(hashKey);
        message.setTopic(newsTopic);
        message.setSuccess(producerHandler.produce(message));
        return message;
    }

}