package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.NoReply;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname NoReplyDao
 * @Author: LeJunWen
 * @Date: 2020/3/8 21:12
 */
@Mapper
public interface NoReplyDao {
    NoReply getNoReplyByThesisNo(String thesisNo);
    int addNoReply(NoReply noReply);
    int  updateNoReply(NoReply noReply);
}
