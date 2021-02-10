package com.abin.eduservice.service.impl;

import com.abin.eduservice.entity.EduTeacher;
import com.abin.eduservice.mapper.EduTeacherMapper;
import com.abin.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 大冰
 * @since 2021-01-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherList(Page<EduTeacher> pageTeacher) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        // 把分页数据封装到pageTeacher中
        baseMapper.selectPage(pageTeacher, wrapper);

        // 获取数据
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean next = pageTeacher.hasNext();
        boolean previous = pageTeacher.hasPrevious();
        // 获取数据存入map集合中 返回

        Map<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("next", next);
        map.put("previous", previous);
        map.put("items",records);
        return map;
    }
}
