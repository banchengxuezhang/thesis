package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.dao.TeacherInfoDao;
import com.jxufe.ljw.thesis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname TeacherServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/27 20:48
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherInfoDao teacherInfoDao;

    @Override
    public int addTeacherInfo(TeacherInfo teacherInfo) {
        return teacherInfoDao.addTeacherInfo(teacherInfo);
    }

    @Override
    public TeacherInfo getTeacherInfo(String userId) {
        return teacherInfoDao.getTeacherInfo(userId);
    }

    @Override
    public int updateTeacherInfo(String userId, String phone, String email) {
        return teacherInfoDao.updateTeacherInfo(userId, phone, email);
    }

    @Override
    public TeacherInfo getTeacherInfoByTeacherNo(String teacherNo) {
        return teacherInfoDao.getTeacherInfoByTeacherNo(teacherNo);
    }

    @Override
    public int updateTeacherInfoByTeacher(TeacherInfo teacherInfo) {
        return teacherInfoDao.updateTeacherInfoByTeacher(teacherInfo);
    }

    @Override
    public int deleteTeacherById(String userId) {
        return teacherInfoDao.deleteTeacherById(userId);
    }
}
