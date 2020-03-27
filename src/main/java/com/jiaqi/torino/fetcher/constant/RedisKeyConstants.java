package com.jiaqi.torino.fetcher.constant;

import java.time.ZonedDateTime;

import com.jiaqi.torino.fetcher.utils.TimeUtils;

public class RedisKeyConstants {
    private final static String FETCHER_DOMAIN_DAILY_STATS = "DAILY_STATS:%s:%s:%s";

    public static String getFetcherDailyDomainStatsKey(String domain, ZonedDateTime date,
            String source) {
        String dateStr = TimeUtils.formatUTC(date, "yyyy-MM-dd");
        return String.format(FETCHER_DOMAIN_DAILY_STATS, dateStr, source, domain);
    }
}