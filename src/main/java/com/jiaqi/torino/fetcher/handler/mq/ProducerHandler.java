package com.jiaqi.torino.fetcher.handler.mq;

import com.jiaqi.torino.fetcher.model.mq.MQMessage;

public interface ProducerHandler {

    Boolean produce(MQMessage msg);

}