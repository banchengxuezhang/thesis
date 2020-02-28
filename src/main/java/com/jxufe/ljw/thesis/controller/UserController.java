package com.jxufe.ljw.thesis.controller;

import com.google.common.collect.Maps;
import com.jxufe.ljw.thesis.bean.StudentInfo;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.StudentService;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.util.Md5Tools;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.EmptyStackException;
import java.util.Map;


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
        @GetMapping("/getInfo")
        public Object getPersionInfo(HttpServletRequest request){
        try {
            LOG.info("==================开始获取用户信息==================");
            User user= (User) request.getSession().getAttribute("user");
            Map<String,Object> res = Maps.newHashMap();
            if(user.getUserType()==UserType.STUDENT.getType()){
                res.put("personInfo",studentService.getStudentInfo(user.getUserId()));
                LOG.info("获取信息："+res+user.getUserId()+user);

            }else {
                res.put("personInfo",teacherService.getTeacherInfo(user.getUserId()));
                LOG.info("获取信息："+res+user+teacherService.getTeacherInfo(user.getUserId()));
            }
            res.put("user",user);
            return ResultUtil.success(res);
        }catch (Exception e){
            LOG.error("【获取到程序出现异常】:",e);
            return ResultUtil.error("查询登录用户信息异常");
        }

        }
      @PostMapping("/updateInfo")
      public Object updatePersionInfo(UserInfoDetail userInfoDetail,HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        try {
            if(user.getUserType()==UserType.STUDENT.getType()){
                studentService.updateStudentInfo(user.getUserId(),userInfoDetail.getPhone(),userInfoDetail.getEmail());
            }else {
                teacherService.updateTeacherInfo(user.getUserId(),userInfoDetail.getPhone(),userInfoDetail.getEmail());
            }
        }catch (Exception e){
            ResultUtil.error("修改用户信息出错！！！");
        }
          return ResultUtil.success("修改用户信息成功！！！");
      }

}
