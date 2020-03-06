package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.StudentInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.dao.StudentInfoDao;
import com.jxufe.ljw.thesis.dao.TeacherInfoDao;
import com.jxufe.ljw.thesis.dao.UserDao;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/26 21:41
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private TeacherInfoDao teacherInfoDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StudentInfoDao studentInfoDao;

    @Override
    public List<User> getUserByAccountAndType(String userAccount, int userType) {
        return userDao.getUserByAccountAndType(userAccount, userType);

    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int updateUserPassword(UserInfoDetail userInfoDetail) {
        return userDao.updateUserPassword(userInfoDetail);
    }

    @Override
    public Object getUserList(int page, int rows, UserInfoDetail userInfoDetail) {
        Map<String,Object> map=new HashMap<>();
        List<UserInfoDetail> userInfoDetailList=new ArrayList<>();
        if(Integer.parseInt(userInfoDetail.getTypeFlag())== UserType.TEACHER.getType()){
            List<UserInfoDetail> teacherList=teacherInfoDao.getTeacherListByDetail(userInfoDetail);
            for (UserInfoDetail t:teacherList
                 ) {
                User user=userDao.getUserById(t.getUserId());
                if(user.getUserType()!=UserType.MANAGE.getType()){
                    t.setUserAccount(t.getTeacherNo());
                    t.setUserName(t.getTeacherName());
                    t.setPhone(t.getTeacherPhone());
                    t.setEmail(t.getTeacherEmail());
                    t.setUserType(UserType.TEACHER.getType());
                    userInfoDetailList.add((UserInfoDetail) ClassUtil.checkNull(t));
                }

            }
        }
        else if(Integer.parseInt(userInfoDetail.getTypeFlag())==UserType.STUDENT.getType()){
            List<UserInfoDetail> studentList=studentInfoDao.getStudentListByDetail(userInfoDetail);
            for (UserInfoDetail s : studentList
                    ) {
                s.setUserName(s.getStudentName());
                s.setUserAccount(s.getStudentNo());
                s.setPhone(s.getStudentPhone());
                s.setEmail(s.getStudentEmail());
                s.setUserType(UserType.STUDENT.getType());
                userInfoDetailList.add((UserInfoDetail) ClassUtil.checkNull(s));
            }
        }else {
            List<UserInfoDetail> teacherList=teacherInfoDao.getTeacherListByDetail(userInfoDetail);
            for (UserInfoDetail t:teacherList
            ) {
                User user=userDao.getUserById(t.getUserId());
                System.out.println("查看userId"+t.getUserId());
                if(user.getUserType()!=UserType.MANAGE.getType()){
                    t.setUserAccount(t.getTeacherNo());
                    t.setUserName(t.getTeacherName());
                    t.setPhone(t.getTeacherPhone());
                    t.setEmail(t.getTeacherEmail());
                    t.setUserType(UserType.TEACHER.getType());
                    userInfoDetailList.add((UserInfoDetail) ClassUtil.checkNull(t));
                }
            }
            List<UserInfoDetail> studentList=studentInfoDao.getStudentListByDetail(userInfoDetail);
            for (UserInfoDetail s : studentList
            ) {
                s.setUserName(s.getStudentName());
                s.setUserAccount(s.getStudentNo());
                s.setPhone(s.getStudentPhone());
                s.setEmail(s.getStudentEmail());
                s.setUserType(UserType.STUDENT.getType());
                userInfoDetailList.add((UserInfoDetail) ClassUtil.checkNull(s));
            }

        }
        List<UserInfoDetail> list=new ArrayList<>();
        return ClassUtil.getObject(page,rows,map,userInfoDetailList,list);
    }

    @Override
    public User getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public int deleteUserById(String userId) {
        return userDao.deleteUserById(userId);
    }

}
