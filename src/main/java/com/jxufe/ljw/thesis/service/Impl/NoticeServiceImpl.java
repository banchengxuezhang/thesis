package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.Notice;
import com.jxufe.ljw.thesis.dao.NoticeDao;
import com.jxufe.ljw.thesis.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname NoticeServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/3/4 22:16
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Override
    public List<Notice> getAllNoticeList() {
        return noticeDao.getAllNoticeList();
    }
}
