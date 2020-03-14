package com.jxufe.ljw.thesis.bean;

import lombok.Data;

import java.util.Date;

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
    private String groupName;

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
    /**
     * 论文答辩状态
     */
    private int status;
    private String opinion;
    private String teacherList;
    private String thesisScoreList;
    private Date replyDate;
    private String replyPlace;
    private  int replyStatus;
    private String replyOpinion;
    private int openReportScore;
    private  int openReportStatus;
    private String openReportOpinion;
    private int reviewStatus;
    private  String reviewOpinion;
    private int inspectionStatus;
    private String inspectionOpinion;
    private int checkStatus;
    private String grouperNo;
    private String grouperName;
    private int groupStatus;
}
