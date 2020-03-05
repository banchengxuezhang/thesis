package com.jxufe.ljw.thesis.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Classname Notice
 * @Author: LeJunWen
 * @Date: 2020/3/4 22:05
 */
@Data
public class Notice {
    private String NoticeId;
    private String noticeTitle;
    private String noticeContent;
    private String noticeUrl;
    private Date createTime;
    private Date updateTime;
    private String noticeStatus;
    private String noticeBelong;
}
