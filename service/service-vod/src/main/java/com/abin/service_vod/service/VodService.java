package com.abin.service_vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/26 12:41
 */
public interface VodService {

    String uploadAliyun(MultipartFile file);

    void removeBatch(List<String> videoList);

    //#43399 add by heliangxin start
    void test(List<String> videoList);
    //#43399 add by heliangxin end
}
