package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.StudentInfo;
import com.jxufe.ljw.thesis.dao.StudentInfoDao;
import com.jxufe.ljw.thesis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname StudentServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/27 20:45
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentInfoDao studentInfoDao;

    @Override
    public int addStudentInfo(StudentInfo studentInfo) {
        return studentInfoDao.addStudentInfo(studentInfo);
    }

    @Override
    public StudentInfo getStudentInfo(String userId) {
        return studentInfoDao.getStudentInfo(userId);
    }

    @Override
    public StudentInfo getStudentInfoByStudentNo(String studentNo) {
        return studentInfoDao.getStudentInfoByStudentNo(studentNo);
    }

    @Override
    public int updateStudentInfo(String studentNo, String phone, String email,String stage) {
        return studentInfoDao.updateStudentInfo(studentNo, phone, email,stage);
    }

    @Override
    public int updateStudentInfoByStudent(StudentInfo studentInfo) {
        return studentInfoDao.updateStudentInfoByStudent(studentInfo);
    }

    @Override
    public int deleteStudentById(String userId) {
        return studentInfoDao.deleteStudentByUserId(userId);
    }
}
