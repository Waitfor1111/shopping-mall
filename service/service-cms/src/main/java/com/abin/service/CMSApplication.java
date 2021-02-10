package com.abin.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/27 16:09
 */
@ComponentScan("com.abin") //指定扫描位置
@EnableDiscoveryClient
@MapperScan("com.abin.service.mapper")  // 扫描mapper文件
@SpringBootApplication
public class CMSApplication {


    public static void main(String[] args) {

        SpringApplication.run(CMSApplication.class, args);
    }

}
