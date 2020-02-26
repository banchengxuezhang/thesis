package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserController
 * @Author: LeJunWen
 * @Date: 2020/2/26 17:24
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public RestResult getUserByAccount(){
        RestResult result=new RestResult();
        User user=new User();
        user.setUserAccount("960407");
        user.setUserType(3);
        result.setResultData(userService.getUserByAccount(user));
        return  result;
    }
}
