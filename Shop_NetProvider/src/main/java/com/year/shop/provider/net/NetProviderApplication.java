package com.year.shop.provider.net;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fyy
 * @date 2019/12/30 0030 下午 21:23
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.fyy.mybabystudy.provider.net.dao")
public class NetProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetProviderApplication.class, args);
    }
}
