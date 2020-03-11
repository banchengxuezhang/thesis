package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.Notice;

import java.util.List;

/**
 * @Classname NoticeService
 * @Author: LeJunWen
 * @Date: 2020/3/4 22:15
 */
public interface NoticeService {
      Object getAllNoticeList(int page,int rows);
      int addNotice(Notice notice);
      Notice getNoticeByNoticeId(String noticeId);
      int deleteNoticeById(String noticeId);
      int updateNotice(Notice notice);
      List<Notice> getNoticeListByUserType(String noticeBelong);
}
