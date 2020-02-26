package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.dao.UserDao;
import com.jxufe.ljw.thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User getUserByAccount(User user) {
        return userDao.getUserByAccountAndType(user.getUserAccount(),user.getUserType());

    }
}
