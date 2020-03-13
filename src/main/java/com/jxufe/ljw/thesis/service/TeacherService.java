package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import javafx.scene.control.Pagination;

import java.util.List;

/**
 * @Classname TeacherService
 * @Author: LeJunWen
 * @Date: 2020/2/27 20:48
 */
public interface TeacherService {
    int addTeacherInfo(TeacherInfo teacherInfo);

    TeacherInfo getTeacherInfo(String userId);

    int updateTeacherInfo(String userId, String telephone, String email);

    TeacherInfo getTeacherInfoByTeacherNo(String teacherNo);
    int updateTeacherInfoByTeacher(TeacherInfo teacherInfo);
    int updateGroupNameByGroupName(String groupName);
    int deleteTeacherById(String userId);
    List<TeacherInfo> getTeacherByGroupName(String groupName);
    List<TeacherInfo> getAllTeachers();
    Object getTeacherInfoForManager(int page,int rows, TeacherInfo teacherInfo);
    int updateTeacherGroupName(String userId,String groupName);
}
