package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.StudentInfo;

/**
 * @Classname StudentService
 * @Author: LeJunWen
 * @Date: 2020/2/27 20:43
 */
public interface StudentService {
    int addStudentInfo(StudentInfo studentInfo);

    StudentInfo getStudentInfo(String userId);

    StudentInfo getStudentInfoByStudentNo(String studentNo);

    int updateStudentInfo(String studentNo, String telephone, String email,String stage);
    int updateStudentInfoByStudent(StudentInfo studentInfo);
    int deleteStudentById(String userId);

}
