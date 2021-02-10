package com.abin.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/23 20:49
 */
@Data
public class ChapterVo {


    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();


}
