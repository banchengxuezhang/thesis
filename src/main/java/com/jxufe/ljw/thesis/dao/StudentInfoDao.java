package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.StudentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname StudentInfoDao
 * @Author: LeJunWen
 * @Date: 2020/2/27 19:57
 */
@Mapper
public interface StudentInfoDao {
    int addStudentInfo(StudentInfo studentInfo);
}
