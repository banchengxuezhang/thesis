package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.TeacherInfo;

/**
 * @Classname TeacherService
 * @Author: LeJunWen
 * @Date: 2020/2/27 20:48
 */
public interface TeacherService {
    int addTeacherInfo(TeacherInfo teacherInfo);
    TeacherInfo getTeacherInfo(String userId);
    int updateTeacherInfo(String userId,String telephone,String email);
}
