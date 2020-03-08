package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname StudentTeacherRelation
 * @Author: LeJunWen
 * @Date: 2020/2/27 13:20
 */
@Data
public class StudentTeacherRelation {
    private String relationId;
    private String studentNo ;
    private String studentName ;

    private String studentClass;
    private String studentMajor ;
    private String studentInstructor ;
    private String studentEmail;

    private String studentPhone;

    private String teacherNo ;
    private String teacherName ;
    private String teacherTitle ;
    private String teacherEmail;
    private String teacherPhone;

    private String thesisNo ;

    private String thesisTitle ;

    private String teacherOpinion ;

    private Integer opinionFlag;

    private Integer taskStatus;

    private String taskUrl ;
    private int thesisStatus;

    private String thesisUrl;

    private String opinionFlagStr ;
    private  String openReportSummary;
    private String openReportWay;
    private String openReportUrl;
    private String reviewUrl;
    private String reviewContent;
    private String inspectionPass;
    private String inspectionNoPass;
    private String reason;
    private String signName;
    private String mobilePhone;
    private int status;
}
