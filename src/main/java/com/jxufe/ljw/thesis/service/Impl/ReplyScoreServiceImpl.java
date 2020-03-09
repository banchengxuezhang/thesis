package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.ReplyScore;
import com.jxufe.ljw.thesis.dao.ReplyScoreDao;
import com.jxufe.ljw.thesis.service.ReplyScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname ReplyScoreServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/3/9 15:59
 */
@Service("replyScoreService")
public class ReplyScoreServiceImpl implements ReplyScoreService {
    @Autowired
    private ReplyScoreDao replyScoreDao;
    @Override
    public ReplyScore getReplyScoreByThesisNo(String thesisNo) {
        return replyScoreDao.getReplyScoreByThesisNo(thesisNo);
    }

    @Override
    public int addReplyScore(ReplyScore replyScore) {
        return replyScoreDao.addReplyScore(replyScore);
    }

    @Override
    public int updateReplyScore(ReplyScore replyScore) {
        return replyScoreDao.updateReplyScore(replyScore);
    }
}
