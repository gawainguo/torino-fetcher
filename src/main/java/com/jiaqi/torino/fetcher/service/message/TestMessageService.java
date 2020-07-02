package com.jiaqi.torino.fetcher.service.message;

import com.jiaqi.torino.fetcher.model.mq.MQMessage;

public interface TestMessageService {
    MQMessage produceTestMessage(String msg);
}