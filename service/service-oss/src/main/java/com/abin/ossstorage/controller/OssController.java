package com.abin.ossstorage.controller;

import com.abin.ossstorage.service.OssService;
import com.abin.utils.R;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/22 9:42
 */

@EnableDiscoveryClient
@CrossOrigin
@RestController
@RequestMapping("/eduoss")
public class OssController {

    @Resource
    private OssService ossService;


    @PostMapping("/avatar")
    public R updateOssFileAvatar(MultipartFile file) {

        String url = ossService.updateOssAvatar(file);
        return R.ok().data("url",url);
    }
}
