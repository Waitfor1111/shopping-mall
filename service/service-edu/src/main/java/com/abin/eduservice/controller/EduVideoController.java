package com.abin.eduservice.controller;


import com.abin.eduservice.entity.EduChapter;
import com.abin.eduservice.entity.EduVideo;
import com.abin.eduservice.entity.chapter.ChapterVo;
import com.abin.eduservice.providerservice.VodProviderService;
import com.abin.eduservice.service.EduVideoService;
import com.abin.servicebase.exceptionhandler.BizarreException;
import com.abin.utils.R;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Resource
    private EduVideoService videoService;

    @Resource
    private VodProviderService vodProviderService;

    // 添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }


    // 删除小节
//    @HystrixCommand(fallbackMethod = "error",
//            ignoreExceptions = Exception.class,
//            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")})
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id") String id) {
        // 通过小节删除视频id
        EduVideo eduVideo = videoService.getById(id);
        // 获取视频id
        String videoSourceId = eduVideo.getVideoSourceId();
        System.out.println("视频id" + videoSourceId);
        // 如果视频id不为空 删除阿里云中的视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 使用OpenFeign 调用service-vod中的方法
            R removeVideo = vodProviderService.removeVideo(videoSourceId);
            if (removeVideo.getCode() == 20001) {
                throw new BizarreException(20001, "删除视频失败，开启熔断功能 ~_~");
            }
        }
        //  删除小节表中对应id的信息
        videoService.removeById(id);
        return R.ok();
    }


    // 修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

    // 根据id查询小节
    @GetMapping("/getVideo/{videoId}")
    public R getChapter(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);

        return R.ok().data("eduVideo", eduVideo);
    }

}

