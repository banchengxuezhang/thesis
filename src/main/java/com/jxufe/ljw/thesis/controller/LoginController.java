package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 登陆
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Object Login(UserInfoDetail userInfoDetail, HttpServletRequest request){
        HttpSession session=request.getSession();
        try{
            if(!((String)session.getAttribute("loginCode")).toUpperCase().equals(userInfoDetail.getLoginCode().toUpperCase())){
                return ResultUtil.error("验证码输入错误！");
            }
            else {
                User user=userService.getUserByAccountAndType(userInfoDetail.getUserAccount(),userInfoDetail.getUserType());
                if(user==null){
                    return ResultUtil.error("用户信息不存在！");
                }else if(!user.getUserPassword().equals(userInfoDetail.getUserPassword())){
                    return ResultUtil.error("账号或密码出错！！！");
                }
                session.setAttribute("user",user);
                return ResultUtil.success("登录成功！！！");
            }
        }catch (Exception e){
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
    public void login(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
    }


    /**
     * 获取登录用户的值
     *
     * @param request
     * @return
     */
    @GetMapping("/getLoginUserInfo")
    public Object getLoginUserInfo(HttpServletRequest request) {
        return request.getSession().getAttribute("user");
    }
}
