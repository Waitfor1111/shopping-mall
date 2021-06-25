package com.abin.service_vod.service.impl;

import com.abin.service_vod.service.VodService;
import com.abin.service_vod.utils.ConstantVodUtils;
import com.abin.service_vod.utils.InitVideoClient;
import com.abin.servicebase.exceptionhandler.BizarreException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/26 12:41
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadAliyun(MultipartFile file) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeBatch(List<String> videoList) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitVideoClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 把集合中的id转成字符串 如：22,33,44,55
            String videoIds = StringUtils.join(videoList.toArray(),",");
            // 向request设置视频id
            request.setVideoIds(videoIds);
            //
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new BizarreException(20001, "删除视频失败");
        }
    }
}
