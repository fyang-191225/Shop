package com.year.shop.provider.net.config;

import com.year.shop.common.util.JedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: BabyStudy
 * @description:
 * @author: Feri
 * @create: 2019-12-30 14:49
 */
@Configuration
public class JedisConfig {
    @Bean
    public JedisUtil createJU(){
        return new JedisUtil();
    }
}
