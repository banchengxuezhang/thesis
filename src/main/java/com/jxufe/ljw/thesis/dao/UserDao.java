package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxufe.ljw.thesis.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @Classname UserDao
 * @Author: LeJunWen
 * @Date: 2020/2/26 17:58
 */
@Mapper
public interface UserDao  extends BaseMapper<User> {
     List<User> getUserByAccountAndType(String userAccount, int userType);
     int addUser(User user);
}
