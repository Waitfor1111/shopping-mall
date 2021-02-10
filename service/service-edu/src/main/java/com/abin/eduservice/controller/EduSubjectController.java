package com.abin.eduservice.controller;


import com.abin.eduservice.entity.subject.OneSubject;
import com.abin.eduservice.service.EduSubjectService;
import com.abin.utils.R;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-22
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Resource
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {

        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }
    // 课程分类列表(树形)
    @GetMapping("/allSubject")
    public R getAllSubject() {
        List<OneSubject> oneSubjectList = subjectService.getAllSubject();

        return R.ok().data("oneSubjectList",oneSubjectList);
    }


}

