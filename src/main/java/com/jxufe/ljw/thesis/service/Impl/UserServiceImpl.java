package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.dao.UserDao;
import com.jxufe.ljw.thesis.service.UserService;
import com.jxufe.ljw.thesis.vo.UserInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname UserServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/26 21:41
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> getUserByAccountAndType(String userAccount, int userType) {
        return userDao.getUserByAccountAndType(userAccount,userType);

    }

    @Override
    public  int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int updateUserPassword(UserInfoDetail userInfoDetail) {
        return userDao.updateUserPassword(userInfoDetail);
    }

}
