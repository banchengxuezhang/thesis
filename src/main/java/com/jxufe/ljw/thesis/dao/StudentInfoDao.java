package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.StudentInfo;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname StudentInfoDao
 * @Author: LeJunWen
 * @Date: 2020/2/27 19:57
 */
@Mapper
public interface StudentInfoDao {
    int addStudentInfo(StudentInfo studentInfo);

    StudentInfo getStudentInfo(String userId);

    StudentInfo getStudentInfoByStudentNo(String studentNo);

    int updateStudentInfo(String studentNo, String phone, String email,String stage);
    List<UserInfoDetail> getStudentListByDetail(UserInfoDetail userInfoDetail);
    int updateStudentInfoByStudent(StudentInfo studentInfo);
    int deleteStudentByUserId(String userId);
}
