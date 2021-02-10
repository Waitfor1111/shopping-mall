package com.abin.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/28 10:57
 */

@ComponentScan("com.abin")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MsmApplication {


    public static void main(String[] args) {

        SpringApplication.run(MsmApplication.class, args);
    }

}
