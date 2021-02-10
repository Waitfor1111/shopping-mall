package com.abin.eduservice.service.impl;

import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.EduCourseDescription;
import com.abin.eduservice.entity.EduTeacher;
import com.abin.eduservice.entity.frontvo.CourseFrontVo;
import com.abin.eduservice.entity.frontvo.CourseWebVo;
import com.abin.eduservice.entity.vo.CourseInfoVo;
import com.abin.eduservice.entity.vo.CoursePublishVo;
import com.abin.eduservice.mapper.EduCourseMapper;
import com.abin.eduservice.service.EduChapterService;
import com.abin.eduservice.service.EduCourseDescriptionService;
import com.abin.eduservice.service.EduCourseService;
import com.abin.eduservice.service.EduVideoService;
import com.abin.servicebase.exceptionhandler.BizarreException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 大冰
 * @since 2021-01-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    // 注入描述
    @Resource
    private EduCourseDescriptionService courseDescriptionService;

    // 注入小节
    @Resource
    private EduVideoService videoService;

    // 注入章节
    @Resource
    private EduChapterService chapterService;

    // courseInfoVo包含课程信息 和 课程简介信息
    @Transactional(rollbackFor = {RuntimeException.class}) // 出现异常所有操作回滚
    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {
        // 第一步 想edu_course表中添加课程信息
        EduCourse eduCourse = new EduCourse();
        // 把courseInfoVo 中包含的课程信息封装到eduCourse中
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        // 向数据库中插入数据
        int insert = baseMapper.insert(eduCourse);

        if (0 == insert) {
            throw new BizarreException(20001, "课程添加失败");
        }

        // 第二部 向edu_course_description中添加课程简介
        // edu_course表和edu_course_description表是一对一关系

        // 创建课程简介对象
        EduCourseDescription courseDescription = new EduCourseDescription();
        // 课程信息表和课程简介表应是一对一关系
        // edu_course表和edu_course_description表的id要一致
        String courseId = eduCourse.getId();
        courseDescription.setId(courseId);
        // 从courseInfoVo中取出课程简介信息存入简介
        courseDescription.setDescription(courseInfoVo.getDescription());
        // 存入数据库
        courseDescriptionService.save(courseDescription);
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseId(String courseId) {

        // 通过id查询课程
        EduCourse eduCourse = baseMapper.selectById(courseId);
        // 创建vo对象
        CourseInfoVo infoVo = new CourseInfoVo();
        // 把查询出来的eduCourse封装成返回对象对象
        BeanUtils.copyProperties(eduCourse, infoVo);
        // 查询课程简介
        EduCourseDescription description = courseDescriptionService.getById(courseId);
        // 赋值
        infoVo.setDescription(description.getDescription());

        return infoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int updateById = baseMapper.updateById(eduCourse);
        if (updateById == 0) {
            throw new BizarreException(20001, "修改课程失败");
        }
        // 修改描述表
        EduCourseDescription description = new EduCourseDescription();

        BeanUtils.copyProperties(courseInfoVo, description);

        courseDescriptionService.updateById(description);


    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo courseInfo = baseMapper.getPublishCourseInfo(courseId);
        return courseInfo;
    }

    @Transactional // 增加事务
    @Override
    public void removeCourse(String courseId) {
        // 根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        // 根据课程id删除章节

        chapterService.removeChapterByCourseId(courseId);
        // 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        // 根据课程id删除课程本身
        int byId = baseMapper.deleteById(courseId);
        if (byId == 0) {
            throw new BizarreException(20001, "删除失败");
        }


    }

    @Override
    public Map<String, Object> getCourseListInfo(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {// 判断一级id是否为空
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {// 判断二级id是否为空
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {// 关注度 进行排序
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {// 最新
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {// 价格
            wrapper.orderByDesc("price");
        }
        // 查询
        baseMapper.selectPage(coursePage, wrapper);

        // 获取数据
        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean next = coursePage.hasNext();
        boolean previous = coursePage.hasPrevious();
        // 获取数据存入map集合中 返回

        Map<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("next", next);
        map.put("previous", previous);
        map.put("items", records);
        return map;

    }

    @Override
    public CourseWebVo getCourseInfo(String courseId) {

        return baseMapper.getBaseCourseInfo(courseId);
    }
}
