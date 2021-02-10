package com.abin.eduservice.mapper;

import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.frontvo.CourseWebVo;
import com.abin.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 大冰
 * @since 2021-01-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {


    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
