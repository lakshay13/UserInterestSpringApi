package com.suri.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author lakshay13@gmail.com
 */
@EnableEurekaClient
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class ItemSpringApi {

    public static void main(String[] args) {
        SpringApplication.run(ItemSpringApi.class, args);
    }
}
