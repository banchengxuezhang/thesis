package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname StudentTeacherRelationDao
 * @Author: LeJunWen
 * @Date: 2020/2/29 20:21
 */
@Mapper
public interface StudentTeacherRelationDao {
    int addStudentTeacherRelation(StudentTeacherRelation studentTeacherRelation);
    List<StudentTeacherRelation> getStudentTeacherRelationByStudentNo(String studentNo);
}
