package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.ReplyScore;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname ReplyScoreDao
 * @Author: LeJunWen
 * @Date: 2020/3/9 15:33
 */
@Mapper
public interface ReplyScoreDao {
    ReplyScore getReplyScoreByThesisNo(String thesisNo);
    int addReplyScore(ReplyScore replyScore);
    int updateReplyScore(ReplyScore replyScore);
}
