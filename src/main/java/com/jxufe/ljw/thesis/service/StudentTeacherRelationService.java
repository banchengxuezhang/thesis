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
    List<StudentTeacherRelation> getStudentTeacherRelationByStudentNo(String studentNo);
    Object getStudentSelectThesisByTeacherNo(int page ,int rows,String teacherNo);
    int  operateStudent(StudentTeacherRelation studentTeacherRelation);
    int deleteRelationByThesisNo(String thesisNo);
    StudentTeacherRelation getStudentTeacherRelationByThesisNo(String thesisNo);
    Object getAgreeThesisByTeacherNo(int page,int rows,String teacherNo);
    int updateTaskUrlByThesisNo(String taskUrl,String thesisNo);
    int updateThesisUrlByThesisNo(String thesisUrl,String thesisNo);
}
