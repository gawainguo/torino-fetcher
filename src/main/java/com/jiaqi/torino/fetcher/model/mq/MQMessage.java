package com.jiaqi.torino.fetcher.model.mq;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQMessage {

    private String message;

    private String hashKey;

    private String topic;

    private Boolean success;
}