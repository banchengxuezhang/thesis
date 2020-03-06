package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserService
 * @Author: LeJunWen
 * @Date: 2020/2/26 21:36
 */
public interface UserService {
    List<User> getUserByAccountAndType(String userAccount, int userType);

    int addUser(User user);

    int updateUserPassword(UserInfoDetail userInfoDetail);
    Object getUserList(int page, int rows, UserInfoDetail userInfoDetail);
    User getUserById(String userId);
    int deleteUserById(String userId);
}
