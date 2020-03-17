package com.jxufe.ljw.thesis.intercepter;


import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.enumeration.PublicData;
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
    private static final Logger logger = LoggerFactory.getLogger(CommonIntercepter.class);

    /**
     * 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().equals("/thesis/")) {
            response.sendRedirect("/thesis/login.html");
            return false;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/thesis/error.html");
            return false;
        } else {
            for(String s: PublicData.managerController){
                if (request.getRequestURL().toString().contains(s)){
                    if (user.getUserType() > UserType.MANAGE.getType()) {
                        response.sendRedirect("/thesis/error.html");
                        return false;

                    }
                    }
                }

            for(String s: PublicData.teacherController){
                if (request.getRequestURL().toString().contains(s)){
                    if (user.getUserType() > UserType.TEACHER.getType()) {
                        response.sendRedirect("/thesis/error.html");
                        return false;

                    }
                }
            }
            return true;
        }
    }

}
