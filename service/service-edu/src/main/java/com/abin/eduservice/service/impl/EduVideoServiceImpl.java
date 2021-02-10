package com.abin.eduservice.service.impl;

import com.abin.eduservice.entity.EduVideo;
import com.abin.eduservice.mapper.EduVideoMapper;
import com.abin.eduservice.providerservice.VodProviderService;
import com.abin.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-23
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Resource
    private VodProviderService providerService;

    // 根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.select("video_source_id");
        wrapper.eq("course_id", courseId);
        // 查询出来存储在阿里云上的视频id
        List<EduVideo> videoSourceIdList = baseMapper.selectList(wrapper);

        // 把List<EduVideo>转换成List<String>
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < videoSourceIdList.size(); i++) {
            EduVideo eduVideo = videoSourceIdList.get(i);
            // 获取视频id
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                // 把视频id存入集合中
                strings.add(videoSourceId);
            }
        }
        // 不为空
        if (strings.size() > 0) {
            // 远程调用删除阿里云中的视频
            providerService.deleteBatch(strings);
        }
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);

        baseMapper.delete(queryWrapper);

    }
}
