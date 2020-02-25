package com.jiaqi.torino.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FetcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(FetcherApplication.class, args);
    }

}
