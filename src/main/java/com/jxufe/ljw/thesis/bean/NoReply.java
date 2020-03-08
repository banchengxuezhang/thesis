package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname NoReply
 * @Author: LeJunWen
 * @Date: 2020/3/8 21:08
 */
@Data
public class NoReply {
    private String noReplyId;
    private String reason;
    private String signName;
    private String thesisNo;
    private String mobilePhone;
    private int status;
}
