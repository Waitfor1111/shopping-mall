package com.abin.eduservice.controller;


import com.abin.eduservice.entity.EduTeacher;
import com.abin.eduservice.entity.vo.TeacherQuery;
import com.abin.eduservice.service.EduTeacherService;
import com.abin.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-20
 */
@Api("讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;


    @ApiOperation("查询所有讲师接口")
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("list", list);
    }


    @ApiOperation("删除对应ID的讲师接口")
    @DeleteMapping("/del/{id}")
    public R delById(@ApiParam(name = "id", value = "讲师id", required = true)
                     @PathVariable("id") String id) {
        boolean removeById = eduTeacherService.removeById(id);
        if (removeById) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("分页查询接口")
    @GetMapping("/pageList/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") Long current,
                             @PathVariable("limit") Long limit) {
        //mybatis-plus 分页插件
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        eduTeacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal(); //返回的数据数量
        List<EduTeacher> records = pageTeacher.getRecords();  //返回数据集合
        return R.ok().data("total", total).data("rows", records);
    }

    //4 条件查询带分页的方法
    @ApiOperation("多条件组合分页查询接口")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //根据创建时间排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }


    @ApiOperation("添加讲师接口")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher) {
        boolean save = eduTeacherService.save(teacher);

        return save ? R.ok() : R.error();
    }

    //根据讲师id进行查询
    @ApiOperation("根据讲师id进行查询")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //讲师修改功能
    @ApiOperation("讲师修改功能")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);

        return flag ? R.ok() : R.error();
    }


}

