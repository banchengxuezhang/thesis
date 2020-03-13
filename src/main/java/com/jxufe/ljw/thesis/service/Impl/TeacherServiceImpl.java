package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.dao.TeacherInfoDao;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int updateGroupNameByGroupName(String groupName) {
        return teacherInfoDao.updateGroupNameByGroupName(groupName);
    }

    @Override
    public int deleteTeacherById(String userId) {
        return teacherInfoDao.deleteTeacherById(userId);
    }

    @Override
    public List<TeacherInfo> getTeacherByGroupName(String groupName) {
        return teacherInfoDao.getTeacherByGroupName(groupName);
    }

    @Override
    public List<TeacherInfo> getAllTeachers() {
        return teacherInfoDao.getAllTeachers();
    }

    @Override
    public Object getTeacherInfoForManager(int page, int rows, TeacherInfo teacherInfo) {
        Map<String,Object> map=new HashMap<>();
        Page<TeacherInfo> page1=new Page<>(page,rows);
        List<TeacherInfo> list=teacherInfoDao.getTeacherInfoForManager(page1,teacherInfo);
        map.put("total",page1.getTotal());
        map.put("rows",list);
        return map;
    }

    @Override
    public int updateTeacherGroupName(String userId, String groupName) {
        return teacherInfoDao.updateTeacherGroupName(userId,groupName);
    }


}
