package com.abin.ossstorage.service.impl;

import com.abin.ossstorage.service.OssService;
import com.abin.ossstorage.utils.StorageUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/22 9:48
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String updateOssAvatar(MultipartFile file) {

        //地域地址
        String endPoint = StorageUtils.END_POIND;

        //密钥
        String keyId = StorageUtils.ACCESS_KEY_ID;
        String keySecret = StorageUtils.ACCESS_KEY_SECRET;

        // bucket 名称
        String bucketName = StorageUtils.BUCKET_NAME;

        try {

            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);

            //获取文件名
            String fileName = file.getOriginalFilename();

            InputStream inputStream = file.getInputStream();

            //产生随机字符串  避免上传的文件名称一样
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            //获取上传时间 根据上传的时间分别管理文件
            String datePath = new DateTime().toString("yyyy/MM/dd");

            // 随机字符串和真实文件名称拼接
            fileName = datePath + "/" + uuid + fileName;


            ossClient.putObject(bucketName, fileName, inputStream);

            //关闭流
            ossClient.shutdown();

            // 返回文件上传路径
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;

            return url;
            // 每天进步一点点
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
