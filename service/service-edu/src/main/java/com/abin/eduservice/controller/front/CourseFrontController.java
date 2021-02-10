package com.abin.eduservice.controller.front;

import com.abin.eduservice.entity.EduCourse;
import com.abin.eduservice.entity.chapter.ChapterVo;
import com.abin.eduservice.entity.frontvo.CourseFrontVo;
import com.abin.eduservice.entity.frontvo.CourseWebVo;
import com.abin.eduservice.service.EduChapterService;
import com.abin.eduservice.service.EduCourseService;
import com.abin.utils.R;
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
@RequestMapping("/eduservice/coursefront")
@RestController
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;


    // 条件查询带分页查询课程
    @PostMapping("/getCourseList/{page}/{limit}")
    public R getCourseList(@PathVariable Long page,
                           @PathVariable Long limit,
                           @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        // 分页插件
        Page<EduCourse> coursePage = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseListInfo(coursePage, courseFrontVo);

        return R.ok().data(map);
    }

    // 课程详情信息
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId) {
        System.out.println("CourseId是：=====" + courseId);
        // 根据课程id 查询课程基本信息
        CourseWebVo courseWebVo = courseService.getCourseInfo(courseId);

        // 根据课程id 查询出课程章节和小节

        List<ChapterVo> chapterAndVideo = chapterService.getChapterAndVideo(courseId);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterAndVideo);
    }

}
