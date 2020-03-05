package com.jxufe.ljw.thesis.intercepter;


import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.enumeration.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Classname TaskDao
 * @Author: LeJunWen
 * @Date: 2020/2/24 22:35
 */
@Component
public class CommonIntercepter implements HandlerInterceptor {
    private static final Logger logger= LoggerFactory.getLogger(CommonIntercepter.class);
    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().equals("/thesis/")){
            response.sendRedirect("/thesis/login.html");
            return false;
        }
        //每一个项目对于登陆的实现逻辑都有所区别，我这里使用最简单的Session提取User来验证登陆。
        HttpSession session = request.getSession();
        //这里的User是登陆时放入session的
        User user = (User) session.getAttribute("user");
        //如果session中没有user，表示没登陆
        if (user == null) {
            //这个方法返回false表示忽略当前请求，如果一个用户调用了需要登陆才能使用的接口，如果他没有登陆这里会直接忽略掉
            response.sendRedirect("/thesis/error.html");
            return false;
        } else {
            if (request.getRequestURL().toString().contains("updateMenuStatus") || request.getRequestURL().toString().contains("/init") || request.getRequestURL().toString().contains("addStudent") || request.getRequestURL().toString().contains("addTeacher") || request.getRequestURL().toString().contains("doc.html") || request.getRequestURL().toString().contains("druid")) {
                if (user.getUserType() > UserType.MANAGE.getType()) {
                    response.sendRedirect("/thesis/error.html");
                    return false;
                }
            }
            if (request.getRequestURL().toString().contains("uploadTask") || request.getRequestURL().toString().contains("getStudentSelectThesisByTeacherNo") || request.getRequestURL().toString().contains("getAgreeThesisByTeacherNo") || request.getRequestURL().toString().contains("getThesisInfoByTeacherNo") || request.getRequestURL().toString().contains("addThesis") || request.getRequestURL().toString().contains("updateThesis") || request.getRequestURL().toString().contains("deleteThesisInfoByThesisIds") || request.getRequestURL().toString().contains("operateStudent")) {
                if (user.getUserType() > UserType.TEACHER.getType()) {
                    response.sendRedirect("/thesis/error.html");
                    return false;

                }
            }
            return true;    //如果session里有user，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
        }
    }

}
