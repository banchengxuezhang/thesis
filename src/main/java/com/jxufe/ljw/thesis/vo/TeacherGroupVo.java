package com.jxufe.ljw.thesis.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Classname TeacherGroupVo
 * @Author: LeJunWen
 * @Date: 2020/3/12 23:54
 */
@Data
public class TeacherGroupVo {
    private String groupId;
    private String groupName;
    private String grouperNo;
    private String grouperName;
    private String replyPlace;
    private Date replyDate;
    private String userId;
    private String teacherId;
    private String teacherNo ;

    private String teacherName ;

    private String teacherTitle ;
    private String teacherEducation;
    private String teacherPhone;

    private String teacherEmail ;
    private String studentNo;

    private String studentName ;
}
