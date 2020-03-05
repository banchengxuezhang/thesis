package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname Init
 * @Author: LeJunWen
 * @Date: 2020/2/27 13:18
 */
@Data
public class Init {
    private String initId;

    private Integer studentNum;

    private Integer teacherNum;

    private Integer teacherGive;

    private String notesForTeacher;

    private String notesForStudent;

    private String notesForManager;
}
