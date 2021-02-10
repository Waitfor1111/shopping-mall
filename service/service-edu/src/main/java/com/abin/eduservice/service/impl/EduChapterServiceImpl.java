package com.abin.eduservice.service.impl;

import com.abin.eduservice.entity.EduChapter;
import com.abin.eduservice.entity.EduVideo;
import com.abin.eduservice.entity.chapter.ChapterVo;
import com.abin.eduservice.entity.chapter.VideoVo;
import com.abin.eduservice.mapper.EduChapterMapper;
import com.abin.eduservice.service.EduChapterService;
import com.abin.eduservice.service.EduVideoService;
import com.abin.servicebase.exceptionhandler.BizarreException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterAndVideo(String courseId) {

        // 1.根据课程id查询所有章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper();
        chapterQueryWrapper.eq("course_id", courseId);
        // 查询出来的章节
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterQueryWrapper);
        // 2.用课程id查询课程中的小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        // 查询出来所有小节
        List<EduVideo> eduVideoList = videoService.list(videoQueryWrapper);
        // 创建list集合 用于最终的数据封装
        List<ChapterVo> finalList = new ArrayList<>();
        // 3.遍历章节集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            // 获取每一个EduChapter
            EduChapter eduChapter = eduChapterList.get(i);
            // 创建返回特殊类型对象
            ChapterVo chapterVo = new ChapterVo();
            // 使用工具，把EduChapter中的数据封装到chapterVo中
            BeanUtils.copyProperties(eduChapter, chapterVo);
            // 放入集合
            finalList.add(chapterVo);
            // 创建集合 用于封装小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                // 获取每一个小节
                EduVideo eduVideo = eduVideoList.get(j);
                // 比较小节里ChapterId和章节里id是否相等
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    @Override
    public Boolean deleteChapter(String chapterId) {
        // 根据chapterId 查询是否含有小节
        // 如果有小节 不能删除
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);

        int eduVideoList = videoService.count(queryWrapper);

        if (eduVideoList > 0) {
            throw new BizarreException(20001, "删除失败");
        } else {
            int deleteById = baseMapper.deleteById(chapterId);
            return deleteById>0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


}
