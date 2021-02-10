package com.abin.service_vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/26 9:40
 */
@EnableDiscoveryClient
@ComponentScan("com.abin")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VodApplication {


    public static void main(String[] args) {

        SpringApplication.run(VodApplication.class, args);

    }


}
