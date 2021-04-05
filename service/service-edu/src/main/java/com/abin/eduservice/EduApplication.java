package com.abin.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/20 16:14
 */
//@EnableHystrix
@EnableFeignClients  // 服务调用
@EnableDiscoveryClient // 开启nacos注册
@EnableTransactionManagement // 开启事务支持
@ComponentScan(basePackages = {"com.abin"}) // 扫描别的组件
@MapperScan("com.abin.eduservice.mapper")  // 扫描mapper文件
@SpringBootApplication
public class EduApplication {

    public static void main(String[] args) {

        SpringApplication.run(EduApplication.class, args);

    }

}
