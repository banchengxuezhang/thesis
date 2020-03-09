package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.ReplyScore;

/**
 * @Classname ReplyScoreService
 * @Author: LeJunWen
 * @Date: 2020/3/9 15:58
 */
public interface ReplyScoreService {
    ReplyScore getReplyScoreByThesisNo(String thesisNo);
    int addReplyScore(ReplyScore replyScore);
    int updateReplyScore(ReplyScore replyScore);
}
