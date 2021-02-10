package com.abin.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/22 21:35
 */
// 一级目录
@Data
public class OneSubject {

    private String id;
    private String title;

    //一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();

}
