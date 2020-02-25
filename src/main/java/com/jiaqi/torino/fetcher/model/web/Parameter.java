package com.jiaqi.torino.fetcher.model.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parameter {
    private String key;
    private String value;
}