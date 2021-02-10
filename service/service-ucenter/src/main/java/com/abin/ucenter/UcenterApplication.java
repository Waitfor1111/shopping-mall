package com.abin.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/28 17:13
 */
@MapperScan("com.abin.ucenter.mapper" )
@ComponentScan("com.abin")
@SpringBootApplication
public class UcenterApplication {


    public static void main(String[] args) {

        SpringApplication.run(UcenterApplication.class, args);
    }

}
