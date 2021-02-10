package com.abin.ossstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/22 9:03
 */
@ComponentScan("com.abin")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class StorageApplication {


    public static void main(String[] args) {

        SpringApplication.run(StorageApplication.class, args);
    }

}
