package com.abin.eduservice.providerservice;

import com.abin.utils.R;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/26 18:27
 */
@Component
@FeignClient(value = "service-vod",fallback = VodProviderServiceImpl.class)
public interface VodProviderService {



    // 根据视频id删除阿里云中上传的视频
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id);

    // 使用feign调用别的服务
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
