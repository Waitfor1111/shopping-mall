package com.abin.service_vod.controller;

import com.abin.service_vod.service.VodService;
import com.abin.service_vod.utils.ConstantVodUtils;
import com.abin.service_vod.utils.InitVideoClient;
import com.abin.servicebase.exceptionhandler.BizarreException;
import com.abin.utils.R;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.tomcat.jni.Time;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/26 12:40
 */
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Resource
    private VodService vodService;

    @PostMapping("/upload")
    public R uploadVideo(MultipartFile file) {

        String videoId = vodService.uploadAliyun(file);
        return R.ok().data("videoId", videoId);
    }

    // 根据视频id删除阿里云中上传的视频
    @DeleteMapping("/removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitVideoClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request设置视频id
            request.setVideoIds(id);
            //
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new BizarreException(20001, "删除视频失败");
        }
    }

    // 批次删除视频
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {

        vodService.removeBatch(videoList);

        return R.ok();
    }

    // 根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id") String id) {

        System.out.println("视频凭证：" + id);
        try {
            // 创建初始化对象
            DefaultAcsClient client = InitVideoClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取视频凭证的request 和 response 对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 设置视频id
            request.setVideoId(id);
            // 调用方法获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获取到的视频凭证
            String playAuth = response.getPlayAuth();
            System.out.println("获取到的视频凭证" + playAuth);
            return R.ok().data("payAuth", playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizarreException(20001, "获取视频凭证失败");
        }
    }


}
