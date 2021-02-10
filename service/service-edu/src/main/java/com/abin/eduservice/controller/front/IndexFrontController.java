package com.abin.eduservice.controller.front;

import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.EduTeacher;
import com.abin.eduservice.service.EduCourseService;
import com.abin.eduservice.service.EduTeacherService;
import com.abin.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/27 16:40
 */
@RestController
@CrossOrigin
@RequestMapping("eduservice/indexFront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    // 查询前8的热门课程 和 查询前四的讲师
    @Cacheable(key = "'courseAndTeacher'",value = "All")
    @GetMapping("/index")
    public R  popularCourse(){
        // 查询前8的热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 拼接sql 拿出后两条数据
        wrapper.last("limit 8");

        //查询前四的讲师

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        // 拼接sql 拿出后两条数据
        wrapperTeacher.last("limit 4");
        // 前八的课程集合
        List<EduCourse> courseList = courseService.list(wrapper);
        // 前四的讲师
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("eduList",courseList).data("teacherList",teacherList);
    }




}
