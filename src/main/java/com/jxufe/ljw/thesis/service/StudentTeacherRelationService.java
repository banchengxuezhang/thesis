package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;

import java.util.List;

/**
 * @Classname StudentTeacherRelationService
 * @Author: LeJunWen
 * @Date: 2020/2/29 20:21
 */
public interface StudentTeacherRelationService {
    int addStudentTeacherRelation(StudentTeacherRelation studentTeacherRelation);
    List<StudentTeacherRelation> getStudentTeacherRelationByStudentNo(String studentId);
}
