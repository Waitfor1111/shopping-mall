package com.abin.eduservice.controller;


import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.vo.CourseInfoVo;
import com.abin.eduservice.entity.vo.CoursePublishVo;
import com.abin.eduservice.entity.vo.CourseQuery;
import com.abin.eduservice.service.EduCourseService;
import com.abin.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Resource
    private EduCourseService courseService;

    // 课程列表
//    public R getCourseList(){
//
//        List<EduCourse> list = courseService.list(null);
//        return R.ok().data("list",list);
//    }
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {

        Page<EduCourse> page = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title",courseQuery.getTitle());
        }

        if(!StringUtils.isEmpty(courseQuery.getStatus())) {
            wrapper.eq("status",courseQuery.getStatus());
        }

        if(!StringUtils.isEmpty(courseQuery.getBegin())) {
            wrapper.ge("gmt_create",courseQuery.getBegin());
        }

        if(!StringUtils.isEmpty(courseQuery.getEnd())) {
            wrapper.le("gmt_modified",courseQuery.getEnd());
        }
        //根据创建时间排序
        wrapper.orderByDesc("gmt_create");

        courseService.page(page,wrapper);

        long total = page.getTotal(); //总记录数
        List<EduCourse> records = page.getRecords();  //list集合

        return R.ok().data("total",total).data("rows",records);
    }



    // 添加课程
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.saveCourse(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    // 根据课程id查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {

        CourseInfoVo courseInfoVo = courseService.getCourseId(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }


    // 根据id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable("courseId") String courseId) {

        CoursePublishVo courseInfo = courseService.getPublishCourseInfo(courseId);

        return R.ok().data("publishCourse", courseInfo);
    }

    // 课程最终发布 修改课程状态
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId) {

        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");

        courseService.updateById(course);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }



}

