package com.jxufe.ljw.thesis.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Classname ReplyScore
 * @Author: LeJunWen
 * @Date: 2020/3/9 15:18
 */
@Data
public class ReplyScore {
    public String replyScoreId;
    private String thesisNo;
    private String teacherList;
    private String thesisScoreList;
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
}
