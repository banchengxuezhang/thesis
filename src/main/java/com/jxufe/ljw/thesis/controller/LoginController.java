package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Classname UserController
 * @Author: LeJunWen
 * @Date: 2020/2/26 17:24
 */
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Object Login(User user,HttpServletRequest request){
        HttpSession session=request.getSession();
        if(!((String)session.getAttribute("loginCode")).toUpperCase().equals(user.getLoginCode().toUpperCase())){
            System.out.println();
            return ResultUtil.error("验证码输入错误！");
        }
        else {
            User u=userService.getUserByAccount(user);
            if(u==null){
                return ResultUtil.error("用户信息不存在！");
            }else if(!u.getUserPassword().equals(user.getUserPassword())){
                return ResultUtil.error("账号或密码出错！！！");
            }
            session.setAttribute("user",user);
            return ResultUtil.success("登录成功！！！");
        }
    }
    /**
     * 退出登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginOut")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
    }


    /**
     * 获取登录用户的值
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLoginUserInfo")
    public Object getLoginUserInfo(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }
}