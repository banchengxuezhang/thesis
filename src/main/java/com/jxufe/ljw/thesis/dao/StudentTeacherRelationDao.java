package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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
    List<StudentTeacherRelation> getStudentSelectThesisByTeacherNo(Pagination pagination, String teacherNo,int flag);
    int operateStudent(StudentTeacherRelation studentTeacherRelation);
    int deleteRelationByThesisNo(String thesisNo);
    StudentTeacherRelation getStudentTeacherRelationByThesisNo(String thesisNo);
    int updateTaskUrlByThesisNo(String taskUrl,String thesisNo);
    int updateThesisUrlByThesisNo(String thesisUrl,String thesisNo);
}
