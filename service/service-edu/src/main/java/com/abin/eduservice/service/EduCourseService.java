package com.abin.eduservice.service;

import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.frontvo.CourseFrontVo;
import com.abin.eduservice.entity.frontvo.CourseWebVo;
import com.abin.eduservice.entity.vo.CourseInfoVo;
import com.abin.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 大冰
 * @since 2021-01-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseId(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    void removeCourse(String courseId);

    Map<String, Object> getCourseListInfo(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getCourseInfo(String courseId);

}
