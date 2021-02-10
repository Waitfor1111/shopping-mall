package com.abin.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/20 16:25
 */
@Configuration
public class EduConfig {

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }



    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
