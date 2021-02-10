package com.abin.eduservice.service;

import com.abin.eduservice.entity.EduChapter;
import com.abin.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-23
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterAndVideo(String courseId);

    Boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
