package com.jxufe.ljw.thesis.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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

    Object getStudentSelectThesisByTeacherNo(int page, int rows, String teacherNo,int opinionFlag);

    int getStudentSelectThesisAgreeNumByTeacherNo(String teacherNo, int flag);

    int operateStudent(StudentTeacherRelation studentTeacherRelation);

    int deleteRelationByThesisNo(String thesisNo);

    StudentTeacherRelation getStudentTeacherRelationByThesisNo(String thesisNo);

    Object getAgreeThesisByTeacherNo(int page, int rows, String teacherNo);
    List<StudentTeacherRelation> getStudentAgreeByTeacherNo(String teacherNo);
    int updateTaskUrlByThesisNo(String taskUrl, String thesisNo);

    int updateThesisUrlByThesisNo(String thesisUrl, String thesisNo);

    Object getAgreeThesisByStudentNo(int page, int rows, String studentNo, int opinionFlag);

    Object getAllStudentTeacherDetail(int page, int rows, StudentTeacherRelation studentTeacherRelation);

    int getAllDealNum(int opinionFlag);
    int deleteRelationByAccount(String userAccount);
}
