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
     *用户姓名
     */
    private String userName;
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
    private String studentId;

    private String studentNo;

    private String studentName ;

    private String studentMajor;

    private String studentInstructor;

    private String studentClass;

    private String studentPhone;

    private String studentEmail ;

    private String studentStage;
    private String teacherId;
    private String teacherNo ;

    private String teacherName ;

    private String teacherTitle ;
    private String teacherEducation;
    private String teacherPhone;

    private String teacherEmail ;
    private String groupName;
    private String typeFlag;
}
