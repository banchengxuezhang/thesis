package com.jxufe.ljw.thesis.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname CommonDao
 * @Author: LeJunWen
 * @Date: 2020/3/3 23:36
 */
@Mapper
public interface CommonDao {
    int getNoThesisStudentNum();
    int getNoStudentTeacherNum();
}
