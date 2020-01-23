package com.year.shop.provider.auto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 21:37
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.year.shop.provider.auto.dao")
public class AutoProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoProviderApplication.class,args);
    }
}
