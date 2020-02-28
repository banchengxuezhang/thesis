package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.StudentInfo;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.StudentService;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.util.Md5Tools;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EmptyStackException;


/**
 * @Classname UserController
 * @Author: LeJunWen
 * @Date: 2020/2/27 18:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @PostMapping("/addStudent")
    public Object addStudent(StudentInfo studentInfo){
        if(userService.getUserByAccountAndType(studentInfo.getStudentNo(), UserType.STUDENT.getType()).size()>0) {
            return ResultUtil.success("用户已存在！！！");
        }
        User user =new User();
        String id="user"+System.currentTimeMillis();
        String stu_id="student"+System.currentTimeMillis();
        user.setUserType(UserType.STUDENT.getType());
        user.setUserAccount(studentInfo.getStudentNo());
        try{
            user.setUserPassword(Md5Tools.convertMD5(studentInfo.getStudentNo()));
        }catch (Exception e){
            LOG.info("==================加密密码失败==================");
            user.setUserPassword(studentInfo.getStudentNo());
        }
        user.setUserId(id);
        studentInfo.setStudentId(stu_id);
        studentInfo.setUserId(id);
        try{
            userService.addUser(user);
            studentService.addStudentInfo(studentInfo);
            LOG.info("==================添加用户信息完成==================");
            return  ResultUtil.success("添加成功！！！");
        }catch (Exception e){
            return ResultUtil.error("添加失败！！！");
        }
    }

    @PostMapping("/addTeacher")
    public Object addTeacher(TeacherInfo teacherInfo){
        if(userService.getUserByAccountAndType(teacherInfo.getTeacherNo(), UserType.TEACHER.getType()).size()>0){
            return  ResultUtil.success("用户已存在！！！");
        }
            User user =new User();
            String id="user"+System.currentTimeMillis();
            String tea_id="teacher"+System.currentTimeMillis();
            user.setUserType(UserType.TEACHER.getType());
            user.setUserAccount(teacherInfo.getTeacherNo());
            try{
                user.setUserPassword(Md5Tools.convertMD5(teacherInfo.getTeacherNo()));
            }catch (Exception e){
                LOG.info("==================加密密码失败==================");
                user.setUserPassword(teacherInfo.getTeacherNo());
            }
            user.setUserId(id);
            teacherInfo.setUserId(id);
            teacherInfo.setTeacherId(tea_id);
            try{
                userService.addUser(user);
                teacherService.addTeacherInfo(teacherInfo);
                return  ResultUtil.success("添加成功！！！");
            }catch (EmptyStackException e){
                return ResultUtil.error("添加异常！！！");
            }

        }

}
