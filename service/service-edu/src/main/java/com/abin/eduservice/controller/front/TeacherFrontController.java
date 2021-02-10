package com.abin.eduservice.controller.front;

import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.EduTeacher;
import com.abin.eduservice.service.EduCourseService;
import com.abin.eduservice.service.EduTeacherService;
import com.abin.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/30 10:02
 */
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
@RestController
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @PostMapping("/getTeacherFrontList/{limit}/{page}")
    public R getTeacherFrontList(@PathVariable("limit") Long limit, @PathVariable("page") Long page) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);

        Map<String, Object> map = teacherService.getTeacherList(pageTeacher);

        return R.ok().data(map);
    }


    @GetMapping("/getTeacherInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable String teacherId) {

        // 根据讲师查询讲师信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        // 根据讲师id查询讲师所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> eduCourses = courseService.list(wrapper);

        return R.ok().data("eduTeacher", eduTeacher).data("courseList", eduCourses);

    }
}
