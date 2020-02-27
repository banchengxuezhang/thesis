package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname NoPassThesis
 * @Author: LeJunWen
 * @Date: 2020/2/27 13:19
 */
@Data
public class NoPassThesis {
    private Integer noPassId;
    private String studentNo;

    private String studentName;

    private String studentClass;
    private String teacherNo;

    private String teacherName;

    private String thesisNo;

    private String thesisTitle;
    private String teacherOpinion;
}
