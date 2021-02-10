package com.abin.eduservice.listener;

import com.abin.eduservice.entity.EduSubject;
import com.abin.eduservice.entity.excel.SubjectData;
import com.abin.eduservice.service.EduSubjectService;
import com.abin.servicebase.exceptionhandler.BizarreException;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/22 19:06
 */
// 读取excel内容  采用一行一行的读取

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    // 因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    // 不能实现数据库操作
    public EduSubjectService subjectService;
    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 读取excel内容  一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new BizarreException(20001,"文件数据为空");
        }
        // 返回的EduSubject不为空 则表示添加的一级目录已经存在  直接把读取到的二级目录与一级目录结合
        // 结合方式是把二级目录的parent_id和一级目录的id保持一致
        EduSubject exitOneSubject = this.exitOneOrTwoSubject(subjectService, subjectData.getOneSubjectName(),"0");
        // 添加一级分类
        if (exitOneSubject == null) { //没有相同的一级分类  进行添加
            //创建对象赋值
            exitOneSubject = new EduSubject();
            exitOneSubject.setParentId("0");
            exitOneSubject.setTitle(subjectData.getOneSubjectName());
            // 添加到数据库
            subjectService.save(exitOneSubject);
        }
        // 获取一级目录id值
        String pid = exitOneSubject.getId();
        // 添加二级分类
        EduSubject exitTwoSubject = this.exitOneOrTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);

        if (exitTwoSubject == null) {
            // 创建对象赋值
            exitTwoSubject = new EduSubject();
            exitTwoSubject.setParentId(pid);
            exitTwoSubject.setTitle(subjectData.getTwoSubjectName());
            // 添加到数据库
            subjectService.save(exitTwoSubject);
        }
    }



    //判断一级或二级目录  如果pid为0 为一级目录
    private EduSubject exitOneOrTwoSubject(EduSubjectService subjectService, String name, String pid) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);

        return subjectService.getOne(wrapper);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
