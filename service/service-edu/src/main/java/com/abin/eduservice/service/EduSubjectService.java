package com.abin.eduservice.service;

import com.abin.eduservice.entity.EduSubject;
import com.abin.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author 大冰
 * @since 2021-01-22
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneAndTwoSubject();

    List<OneSubject> getAllSubject();
}
