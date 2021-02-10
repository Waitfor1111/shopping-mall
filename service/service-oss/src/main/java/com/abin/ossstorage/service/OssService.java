package com.abin.ossstorage.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/22 9:48
 */
public interface OssService {


    String updateOssAvatar(MultipartFile file);

}
