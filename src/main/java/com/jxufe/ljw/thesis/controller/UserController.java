package com.jxufe.ljw.thesis.controller;

import com.google.common.collect.Maps;
import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.util.Md5Tools;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @Classname UserController
 * @Author: LeJunWen
 * @Date: 2020/2/27 18:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private  NoPassThesisService noPassThesisService;
    @Autowired
    private ThesisInfoService thesisInfoService;
    @Autowired
    private IMailService iMailService;
   @Autowired
   private GroupService groupService;
    /**
     * 管理员添加学生
     *
     * @param studentInfo
     * @return
     */
    @Transactional
    @PostMapping("/addStudent")
    public Object addStudent(StudentInfo studentInfo) {
        if (userService.getUserByAccountAndType(studentInfo.getStudentNo(), UserType.STUDENT.getType()).size() > 0) {
            return ResultUtil.success("用户已存在！！！");
        }
        User user = new User();
        String id = "USER" + System.currentTimeMillis();
        String stu_id = "STUDENT" + System.currentTimeMillis();
        user.setUserType(UserType.STUDENT.getType());
        user.setUserAccount(studentInfo.getStudentNo());
        try {
            user.setUserPassword(Md5Tools.convertMD5(studentInfo.getStudentNo()));
        } catch (Exception e) {
            logger.info("==================加密密码失败==================");
            user.setUserPassword(studentInfo.getStudentNo());
        }
        user.setUserId(id);
        studentInfo.setStudentId(stu_id);
        studentInfo.setUserId(id);
        studentInfo.setStudentStage(PublicData.waitSelectThesis);
        try {
            userService.addUser(user);
            studentService.addStudentInfo((StudentInfo) ClassUtil.checkNull(studentInfo));
            logger.info("==================添加用户信息完成==================");
            return ResultUtil.success("添加成功！！！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("添加失败！！！");
        }
    }

    /**
     * 管理员添加教师
     *
     * @param teacherInfo
     * @return
     */
    @Transactional
    @PostMapping("/addTeacher")
    public Object addTeacher(TeacherInfo teacherInfo) {
        if (userService.getUserByAccountAndType(teacherInfo.getTeacherNo(), UserType.TEACHER.getType()).size() > 0) {
            return ResultUtil.success("用户已存在！！！");
        }
        User user = new User();
        String id = "USER" + System.currentTimeMillis();
        String tea_id = "TEACHER" + System.currentTimeMillis();
        user.setUserType(UserType.TEACHER.getType());
        user.setUserAccount(teacherInfo.getTeacherNo());
        try {
            user.setUserPassword(Md5Tools.convertMD5(teacherInfo.getTeacherNo()));
        } catch (Exception e) {
            logger.info("==================加密密码失败==================");
            user.setUserPassword(teacherInfo.getTeacherNo());
        }
        user.setUserId(id);
        teacherInfo.setUserId(id);
        teacherInfo.setTeacherId(tea_id);
        try {
            userService.addUser(user);
            teacherService.addTeacherInfo((TeacherInfo) ClassUtil.checkNull(teacherInfo));
            return ResultUtil.success("添加成功！！！");
        } catch (EmptyStackException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("添加异常！！！");
        }

    }

    /**
     * 用户获取自己的信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getInfo")
    public Object getPersionInfo(HttpServletRequest request) {
        try {
            logger.info("==================开始获取用户信息==================");
            User user = (User) request.getSession().getAttribute("user");
            Map<String, Object> res = Maps.newHashMap();
            if (user.getUserType() == UserType.STUDENT.getType()) {
                res.put("personInfo", studentService.getStudentInfo(user.getUserId()));
                logger.info("获取信息：" + res + user.getUserId() + user);

            } else {
                res.put("personInfo", teacherService.getTeacherInfo(user.getUserId()));
                logger.info("获取信息：" + res + user + teacherService.getTeacherInfo(user.getUserId()));
            }
            res.put("user", user);
            return ResultUtil.success(res);
        } catch (Exception e) {
            logger.error("【获取到程序出现异常】:", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("查询登录用户信息异常");
        }

    }

    /**
     * 用户修改基本信息
     *
     * @param userInfoDetail
     * @param request
     * @return
     */
    @Transactional
    @PostMapping("/updateInfo")
    public Object updatePersionInfo(UserInfoDetail userInfoDetail, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            if (user.getUserType() == UserType.STUDENT.getType()) {
                studentService.updateStudentInfo(user.getUserAccount(), userInfoDetail.getPhone(), userInfoDetail.getEmail(),"");
            } else {
                teacherService.updateTeacherInfo(user.getUserId(), userInfoDetail.getPhone(), userInfoDetail.getEmail());
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("修改用户信息出错！！！");
        }
        return ResultUtil.success("修改用户信息成功！！！");
    }

    /**
     * 用户修改密码
     *
     * @param userInfoDetail 用户密码信息
     * @param request
     * @return
     */
    @PostMapping("/updatePassword")
    public Object updatePassword(UserInfoDetail userInfoDetail, HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (!Md5Tools.convertMD5(user.getUserPassword()).equals(userInfoDetail.getPrePwd())) {
                return ResultUtil.success("原密码错误！！！");
            }
            userInfoDetail.setUserId(user.getUserId());
            userInfoDetail.setNewPwd(Md5Tools.convertMD5(userInfoDetail.getNewPwd()));
            userService.updateUserPassword(userInfoDetail);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("修改密码异常！！！");
        }
        return ResultUtil.success("修改密码成功！！！");
    }
    /**
     * 管理员获取所有用户列表
     *
     */
   @GetMapping("/getUserList")
    public Object getUserList(int page,int rows,UserInfoDetail userInfoDetail){
       return userService.getUserList(page,rows,userInfoDetail);
   }

    /**
     * 管理员获取用户信息
     * @param userId
     * @return
     */
   @GetMapping("/getUserDetailInfoById")
    public Object getUserDetailInfoById(String userId){
       User user=userService.getUserById(userId);
       Map<String,Object> map=new HashMap<>();
       if(user.getUserType()==UserType.STUDENT.getType()){
           map.put("type","3");
           map.put("student",studentService.getStudentInfo(userId));
           return map;
       }else {
           map.put("type","2");
           map.put("teacher",teacherService.getTeacherInfo(userId));
           return map;
       }
   }

    /**
     * 管理员修改教师信息
     * @param teacherInfo
     * @return
     */
   @PostMapping("/updateTeacherInfo")
    public Object updateTeacherInfo(TeacherInfo teacherInfo){
      logger.info("查看修改教师信息："+teacherInfo);
       teacherService.updateTeacherInfoByTeacher(teacherInfo);
       return ResultUtil.success("修改成功！");
   }

    /**
     * 管理员修改学生信息
     * @param studentInfo
     * @return
     */
    @PostMapping("/updateStudentInfo")
    public Object updateStudentInfo(StudentInfo studentInfo){
       logger.info("查看修改学生信息："+studentInfo);
       studentService.updateStudentInfoByStudent(studentInfo);
       return ResultUtil.success("修改成功！");
    }

    /**
     * 管理员删除用户
     * @param userIds
     * @return
     */
    @DeleteMapping("/deleteUsersByIds")
    @Transactional
    public Object deleteUsersByIds(String[] userIds){
        logger.info("查看userIds"+userIds);
        for (String userId:userIds
             ) {
            User user=userService.getUserById(userId);
            noPassThesisService.deleteNoPassByStudentNoOrTeacherNo(user.getUserAccount());
            userService.deleteUserById(userId);
            if(user.getUserType()==UserType.STUDENT.getType()){
                StudentInfo studentInfo=studentService.getStudentInfo(userId);
                if(studentInfo.getStudentEmail()!=""&&studentInfo.getStudentEmail()!=null){
                    iMailService.sendSimpleMail(studentInfo.getStudentEmail(),"很抱歉！您已被管理员删除！",studentInfo.getStudentName()+
                            "同学你好！很抱歉，您已被毕业论文管理系统管理员删除！\n您的论文信息都将被清空！\n如有疑问，请及时联系学院管理员！！！");
                }
                studentService.deleteStudentById(userId);
                List<StudentTeacherRelation> studentTeacherRelations=relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
                if(studentTeacherRelations.size()>0){
                    for (StudentTeacherRelation studentTeacherRelation:studentTeacherRelations){
                        ThesisInfo thesisInfo=new ThesisInfo();
                        thesisInfo.setSelectNum(0);
                        thesisInfo.setThesisId(studentTeacherRelation.getThesisNo());
                        thesisInfoService.updateThesis(thesisInfo);
                    }
                }
            }
            if(user.getUserType()==UserType.TEACHER.getType()){
                TeacherInfo teacherInfo=teacherService.getTeacherInfo(userId);
                logger.info("查看userId"+userId);
                logger.info("查看teacher"+teacherInfo);
                if(teacherInfo.getTeacherEmail()!=null&&teacherInfo.getTeacherEmail()!=""){
                    iMailService.sendSimpleMail(teacherInfo.getTeacherEmail(),"很抱歉！您已被管理员删除！",teacherInfo.getTeacherName()+
                            "教师您好！很抱歉，您已被毕业论文管理系统管理员删除!\n您的论文课题,学员信息都将被清空！\n如有疑问，请及时联系管理员！！！");
                }
                teacherService.deleteTeacherById(userId);
                thesisInfoService.deleteThesisByAccount(user.getUserAccount());
            }
            relationService.deleteRelationByAccount(user.getUserAccount());

        }
      return ResultUtil.success("删除成功！！！");
    }

    /**
     * 管理员获取教师列表
     * @param page
     * @param rows
     * @param teacherInfo
     * @return
     */
    @GetMapping("/getTeacherListForManager")
    public Object getTeacherListForManager(int page,int rows,TeacherInfo teacherInfo){
      List<Group> groups=groupService.getGroupNames();
      List<String> groupNames=new ArrayList<>();
        for (Group g:groups
             ) {
            groupNames.add(g.getGroupName());
        }
        Map<String,Object> map= (Map<String, Object>) teacherService.getTeacherInfoForManager(page,rows,teacherInfo);
        List<TeacherInfo> list= (List<TeacherInfo>) map.get("rows");
        for (TeacherInfo t :list
                ) {
            List<StudentTeacherRelation> studentTeacherRelations=relationService.getStudentAgreeByTeacherNo(t.getTeacherNo());
            String students="";
            for (StudentTeacherRelation s:studentTeacherRelations
                 ) {
                students+=s.getStudentNo()+"-"+s.getStudentName()+" ";
            }
            map.put(t.getTeacherNo(),students);
        }
        map.put("groupNames",groupNames);
        return map;
    }
}
