package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname TeacherInfoDao
 * @Author: LeJunWen
 * @Date: 2020/2/27 19:57
 */
@Mapper
public interface TeacherInfoDao {
    int addTeacherInfo(TeacherInfo teacherInfo);

    TeacherInfo getTeacherInfo(String userId);

    int updateTeacherInfo(String userId, String phone, String email);

    TeacherInfo getTeacherInfoByTeacherNo(String teacherNo);
    List<UserInfoDetail> getTeacherListByDetail(UserInfoDetail userInfoDetail);
    List<TeacherInfo> getTeacherInfoForManager(Pagination pagination, TeacherInfo teacherInfo);
    List<TeacherInfo> getTeacherByGroupName(String groupName);
    List<TeacherInfo> getAllTeachers();
    int updateTeacherInfoByTeacher(TeacherInfo teacherInfo);
    int updateGroupNameByGroupName(String groupName);
    int deleteTeacherById(String userId);
    int updateTeacherGroupName(String userId,String groupName);
}
