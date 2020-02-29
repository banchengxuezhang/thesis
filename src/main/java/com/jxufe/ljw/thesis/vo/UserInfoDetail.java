package com.jxufe.ljw.thesis.vo;

import lombok.Data;

/**
 * @Classname UserInfoDetail
 * @Author: LeJunWen
 * @Date: 2020/2/27 13:04
 */
@Data
public class UserInfoDetail {
    /**
     *用户id
     */
    private String userId;
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
    /**
     * 登录验证码
     */
    private String loginCode;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 邮箱信息
     */
    private String email ;
    /**
     * 原密码
     */
    private String prePwd;
    /**
     * 新密码
     */
    private String newPwd;
    /**
     * 确认新密码
     */
    private String sureNewPwd;
}
