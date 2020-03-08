package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.NoReply;

/**
 * @Classname NoReplyService
 * @Author: LeJunWen
 * @Date: 2020/3/8 21:32
 */
public interface NoReplyService {
    NoReply getNoReplyByThesisNo(String thesisNo);
    int addNoReply(NoReply noReply);
    int  updateNoReply(NoReply noReply);
}
