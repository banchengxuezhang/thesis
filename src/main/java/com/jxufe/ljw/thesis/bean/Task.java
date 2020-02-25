package com.jxufe.ljw.thesis.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Classname Task
 * @Author: LeJunWen
 * @Date: 2020/2/24 21:54
 */
@Component
@Data
public class Task {
    private String taskId;
    private String taskName;
    private Date year;
    private int taskState;
    private Date createTime;
}
