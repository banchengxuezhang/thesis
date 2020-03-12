package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname TeacherInfo
 * @Author: LeJunWen
 * @Date: 2020/2/27 13:20
 */
@Data
public class TeacherInfo {
    private String userId;
    private String teacherId;
    private String teacherNo ;

    private String teacherName ;

    private String teacherTitle ;
    private String teacherEducation;
    private String teacherPhone;

    private String teacherEmail ;
    private String groupName;
}
