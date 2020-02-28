package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname TeacherInfoDao
 * @Author: LeJunWen
 * @Date: 2020/2/27 19:57
 */
@Mapper
public interface TeacherInfoDao {
    int addTeacherInfo(TeacherInfo teacherInfo);
}
