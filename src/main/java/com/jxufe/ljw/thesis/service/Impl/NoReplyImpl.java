package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.NoReply;
import com.jxufe.ljw.thesis.dao.NoReplyDao;
import com.jxufe.ljw.thesis.service.NoReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname NoReplyImpl
 * @Author: LeJunWen
 * @Date: 2020/3/8 21:32
 */
@Service("noReplyService")
public class NoReplyImpl implements NoReplyService {
    @Autowired
    private NoReplyDao noReplyDao;
    @Override
    public NoReply getNoReplyByThesisNo(String thesisNo) {
        return noReplyDao.getNoReplyByThesisNo(thesisNo);
    }

    @Override
    public int addNoReply(NoReply noReply) {
        return noReplyDao.addNoReply(noReply);
    }

    @Override
    public int updateNoReply(NoReply noReply) {
        return noReplyDao.updateNoReply(noReply);
    }
}
