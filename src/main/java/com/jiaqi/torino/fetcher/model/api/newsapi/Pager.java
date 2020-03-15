package com.jiaqi.torino.fetcher.model.api.newsapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pager {

    private Integer page;

    private Integer pageSize;

    public void nextPage() {
        this.page += 1;
    }
}