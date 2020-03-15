package com.jiaqi.torino.fetcher.model.api.newsapi;

import java.time.ZonedDateTime;
import java.util.List;

import com.jiaqi.torino.fetcher.utils.TimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleFilter {

    private List<String> sources;

    private List<String> domains;

    private ZonedDateTime from;

    private ZonedDateTime to;

    private String language;

    private String keyword;

    @Override
    public String toString() {
        return String.format("sources: %s, domains: %s\nfrom: %s, to: %s\nlanguage: %s, keyword: %s",
                sources.toString(), domains.toString(),
                TimeUtils.formatUTC(from), TimeUtils.formatUTC(to), language, keyword);
    }
}
