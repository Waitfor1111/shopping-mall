package com.abin.eduservice.service;

import com.abin.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 大冰
 * @since 2021-01-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherList(Page<EduTeacher> pageTeacher);

}
