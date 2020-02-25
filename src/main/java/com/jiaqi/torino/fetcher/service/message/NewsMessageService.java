package com.jiaqi.torino.fetcher.service.message;

import com.jiaqi.torino.fetcher.model.domain.NewsArticle;
import com.jiaqi.torino.fetcher.model.mq.MQMessage;

public interface NewsMessageService {

    MQMessage produceNewsMessage(NewsArticle news);
}