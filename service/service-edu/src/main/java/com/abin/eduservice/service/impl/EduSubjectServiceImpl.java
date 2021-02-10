package com.abin.eduservice.service.impl;

import com.abin.eduservice.entity.EduSubject;
import com.abin.eduservice.entity.excel.SubjectData;
import com.abin.eduservice.entity.subject.OneSubject;
import com.abin.eduservice.entity.subject.TwoSubject;
import com.abin.eduservice.listener.SubjectExcelListener;
import com.abin.eduservice.mapper.EduSubjectMapper;
import com.abin.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-22
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {

        try {
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 第一个参数是输入流 第二个参数：读取类容返回的类模板  第三个参数：读取监听器
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // 查询一级目录和二级目录 方式一
    @Override
    public List<OneSubject> getAllOneAndTwoSubject() {

        // 第一步 查询一级目录
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        // 满足parent_id = 0
        oneWrapper.eq("parent_id", 0);

        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        // 第二步 查询二级目录
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        // 满足parent_id != 0
        twoWrapper.ne("parent_id", 0);

        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        // 封装一级目录
        // 查询出来所有的一级目录list集合遍历，得到每个一级对象
        List<OneSubject> arrayList = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 获取每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            // 把eduSubject对象中的属性赋值到OneSubject
            OneSubject oneSubject = new OneSubject();
            // 使用工具类把eduSubject属性copy到oneSubject
            BeanUtils.copyProperties(eduSubject, oneSubject);

            arrayList.add(oneSubject);
            // 分装二级目录
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {

                EduSubject tSubject = twoSubjectList.get(j);

                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    //把twoSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();

                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return arrayList;
    }


    // 查询一级目录和二级目录 方式二
    public List<OneSubject> getAllSubject(){
        // 第一步 查询一级目录
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        // 满足parent_id = 0
        oneWrapper.eq("parent_id", 0);

        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);

        List<OneSubject> oneSubjectArrayList = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 查询出来的一级目录
            EduSubject eduSubject = oneSubjectList.get(i);
            // 创建返回一级目录的特殊entity
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            oneSubjectArrayList.add(oneSubject);
            // 一级目录的id
            String id = eduSubject.getId();
            // 通过一级目录的id与二级目录的parent_id 相同 查出二级目录
            QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("parent_id",id);
            // 二级目录存在多个
            List<EduSubject> eduSubjectList = baseMapper.selectList(queryWrapper);
            // 把二级目录对应的entity 存放在List集合中
            List<TwoSubject> twoSubjectList = new ArrayList<>();

            for (int j = 0; j < eduSubjectList.size(); j++) {
                // 获取与一级目录id相同的每个二级目录
                EduSubject tSubject = eduSubjectList.get(j);
                // 创建返回二级目录的特殊entity
                TwoSubject twoSubject = new TwoSubject();
                // 把tSubject中的属性和twoSubject中
                BeanUtils.copyProperties(tSubject,twoSubject);
                //  放入list集合中
                twoSubjectList.add(twoSubject);
            }
            // 把数据封装在返回类型OneSubject对象中
            oneSubject.setChildren(twoSubjectList);
        }
        // 返回List<OneSubject>
        return oneSubjectArrayList;
    }

}
