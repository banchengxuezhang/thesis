package com.jxufe.ljw.thesis.bean;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @Classname Notice
 * @Author: LeJunWen
 * @Date: 2020/3/4 22:05
 */
@Data
public class Notice {
    /**
     * 文件
     */
    private MultipartFile file;
    private String NoticeId;
    private String noticeTitle;
    private String noticeUrl;
    private Date createTime;
    private Date updateTime;
    private String noticeStatus;
    private String noticeBelong;
}
