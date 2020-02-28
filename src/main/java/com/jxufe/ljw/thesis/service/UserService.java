package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.User;

import java.util.List;

/**
 * @Classname UserService
 * @Author: LeJunWen
 * @Date: 2020/2/26 21:36
 */
public interface UserService {
    List<User > getUserByAccountAndType(String userAccount, int userType);
    int addUser(User user);
}
