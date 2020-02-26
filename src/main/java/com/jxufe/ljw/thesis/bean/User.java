package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname User
 * @Author: LeJunWen
 * @Date: 2020/2/26 17:24
 */
@Data
public class User {
    /**
    *用户id
     */
    private String id;
    /**
     *用户账户
     */
    private String userAccount;
    /**
     *用户密码
     */
    private String userPassword;
    /**
     * 用户类型
     */
    private int userType;
}
