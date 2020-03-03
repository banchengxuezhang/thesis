package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.NoPassThesis;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname NoPassThesis
 * @Author: LeJunWen
 * @Date: 2020/3/3 13:12
 */
@Mapper
public interface NoPassThesisDao {
    int addNoPassThesis(NoPassThesis noPassThesis);

    List<NoPassThesis> getNoPassThesisByStudentNo(String studentNo);

    List<StudentTeacherRelation> getSelectAll(@Param("relation") StudentTeacherRelation studentTeacherRelation);

    int getNoPassNum();
}
