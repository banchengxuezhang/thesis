package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.Notice;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname NoticeDao
 * @Author: LeJunWen
 * @Date: 2020/3/4 22:11
 */
@Mapper
public interface NoticeDao {
    List<Notice> getAllNoticeList(Pagination pagination);
    Notice getNoticeByNoticeId(String noticeId);
    int addNotice(Notice notice);
    int updateNotice(Notice notice);
    int deleteNoticeById(String noticeId);
    List<Notice> getNoticeListByUserType(String noticeBelong);
}
