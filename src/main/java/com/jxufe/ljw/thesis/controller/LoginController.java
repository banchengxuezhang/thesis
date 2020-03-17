package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.util.Md5Tools;
import com.jxufe.ljw.thesis.util.VerifyCodeUtils;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Classname UserController
 * @Author: LeJunWen
 * @Date: 2020/2/26 17:24
 */
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    /**
     * 登陆
     *
     * @param request        请求
     * @param userInfoDetail 前端提供的登录数据
     * @return
     */
    @Transactional
    @PostMapping("/login")
    public Object Login(UserInfoDetail userInfoDetail, HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("---------------------查看Session:" + session.getId() + "----------------------------");
        try {
            if (!((String) session.getAttribute("loginCode")).toUpperCase().equals(userInfoDetail.getLoginCode().toUpperCase())) {
                return ResultUtil.error("验证码输入错误！");
            } else {

                List<User> users = userService.getUserByAccountAndType(userInfoDetail.getUserAccount(), userInfoDetail.getUserType());
                if (users.size() < 1) {
                    if(userInfoDetail.getUserType()==2){
                        users=userService.getUserByAccountAndType(userInfoDetail.getUserAccount(),userInfoDetail.getUserType()-1);
                    if(users.size()<1){
                        return ResultUtil.error("用户信息不存在！！！");
                    }else {
                        users.get(0).setUserType(2);
                    }
                    }else {
                        return ResultUtil.error("用户信息不存在！！！");
                    }

                }
                User user = users.get(0);
                if (!Md5Tools.convertMD5(user.getUserPassword()).equals(userInfoDetail.getUserPassword())) {
                    try {
                        logger.info("------------------------查看user:" + user);
                        String password = (String) session.getAttribute(user.getUserAccount() + "password");
                        logger.info("------------------------------查看password:" + password);
                        if (userInfoDetail.getUserPassword().equals(password)) {
                            session.setAttribute("user", user);
                            userInfoDetail.setNewPwd(Md5Tools.convertMD5(password));
                            userInfoDetail.setUserId(user.getUserId());
                            userService.updateUserPassword(userInfoDetail);
                            session.removeAttribute(user.getUserAccount() + "password");
                            user.setUserPassword(Md5Tools.convertMD5(password));
                            session.setAttribute("user", user);
                            return ResultUtil.success("登录成功！！！");
                        }
                    } catch (Exception e) {
                        logger.info("session中无密码！");
                    }
                    return ResultUtil.error("账号或密码错误！！！");
                }
                session.setAttribute("user", user);
                session.removeAttribute(user.getUserAccount() + "password");
                return ResultUtil.success("登录成功！！！");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("请刷新页面！");
        }

    }

    /**
     * 退出登陆
     *
     * @param request
     * @return
     */
    @PostMapping("/loginOut")
    public void loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }


    /**
     * 获取登录用户的信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getLoginUserInfo")
    public Object getLoginUserInfo(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }

    /**
     * 忘记密码设置
     *
     * @param userAccount
     * @param userType
     * @param request
     * @return
     */
    @PostMapping("/forgetPassword")
    public Object forgetPassword(String userAccount, int userType, HttpServletRequest request) {
        try {
            if (userType == UserType.STUDENT.getType()) {
                StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(userAccount);
                if (studentInfo.getStudentEmail() != null && studentInfo.getStudentEmail() != "") {
                    String password = VerifyCodeUtils.generateVerifyCode(8);
                    iMailService.sendSimpleMail(studentInfo.getStudentEmail(), "重置密码成功！请查看您的新密码！", studentInfo.getStudentName() + "同学您好，您的毕业论文管理系统密码已被重置！" +
                            "\n如非本人操作，请无视此条信息。\n如未登录，此密码自动失效。\n您的新密码为：\n" + password
                    );
                    request.getSession().setAttribute(userAccount + "password", password);
                    return ResultUtil.success("您的密码重置成功！已将新密码发送至您的邮箱！\n请查看登录，登录成功前不要退出浏览器！");
                } else {
                    return ResultUtil.error("发送邮件失败！未设置邮箱，请联系学院管理员获取密码！");
                }

            } else {
                TeacherInfo teacherInfo = teacherService.getTeacherInfoByTeacherNo(userAccount);
                if (teacherInfo.getTeacherEmail() != null && teacherInfo.getTeacherEmail() != "") {
                    String password = VerifyCodeUtils.generateVerifyCode(8);
                    iMailService.sendSimpleMail(teacherInfo.getTeacherEmail(), "重置密码成功！请查看您的新密码！", teacherInfo.getTeacherName() + "教师您好，您的毕业论文管理系统密码已被重置！" +
                            "\n如非本人操作，请无视此条信息。\n如未登录，此密码自动失效。\n您的新密码为：\n" + password
                    );
                    request.getSession().setAttribute(userAccount + "password", password);
                    logger.info("查看存储的密码：" + userAccount + "password:" + request.getSession().getAttribute(userAccount + "password"));
                    logger.info("---------------------查看Session:" + request.getSession().getId() + "----------------------------");
                    return ResultUtil.success("您的密码重置成功！已将新密码发送至您的邮箱！\n请查看登录，登录成功前不要退出浏览器！");
                } else {
                    return ResultUtil.error("发送邮件失败！未设置邮箱，请联系学院管理员获取密码！");
                }

            }

        } catch (Exception e) {
            return ResultUtil.error("重置密码失败！请检查用户名和角色！！！");
        }
    }
}
