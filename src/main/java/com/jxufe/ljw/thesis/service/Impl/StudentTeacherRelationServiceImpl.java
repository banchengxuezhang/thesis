package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.dao.StudentTeacherRelationDao;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname StudentTeacherRelationServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/29 20:20
 */
@Service("studentTeacherRelationService")
public class StudentTeacherRelationServiceImpl implements StudentTeacherRelationService {
    @Autowired
    private StudentTeacherRelationDao studentTeacherRelationDao;

    @Override
    public int addStudentTeacherRelation(StudentTeacherRelation studentTeacherRelation) {
        return studentTeacherRelationDao.addStudentTeacherRelation(studentTeacherRelation);
    }

    @Override
    public List<StudentTeacherRelation> getStudentTeacherRelationByStudentNo(String studentId) {
        return studentTeacherRelationDao.getStudentTeacherRelationByStudentNo(studentId);
    }
}
