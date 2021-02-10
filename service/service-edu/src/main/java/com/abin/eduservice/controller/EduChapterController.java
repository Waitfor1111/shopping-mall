package com.abin.eduservice.controller;


import com.abin.eduservice.entity.EduChapter;
import com.abin.eduservice.entity.chapter.ChapterVo;
import com.abin.eduservice.service.EduChapterService;
import com.abin.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Resource
    private EduChapterService chapterService;

    // 查询课程大纲列表  根据课程id进行查询
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){

        List<ChapterVo> list = chapterService.getChapterAndVideo(courseId);

       return R.ok().data("list",list);
    }

    // 添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    // 根据id查询章节
    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }
    // 修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return  R.ok();
    }

    // 删除章节
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){

        Boolean deleteChapter = chapterService.deleteChapter(chapterId);
        if (deleteChapter){
            return R.ok();
        }
        return R.error();
    }


}

