package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jxufe.ljw.thesis.bean.Notice;
import com.jxufe.ljw.thesis.dao.NoticeDao;
import com.jxufe.ljw.thesis.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Object getAllNoticeList(int page,int rows) {
        Map<String,Object>map=new HashMap<>();
        Page<Notice> page1=new Page<>(page,rows);
        List<Notice> notices= noticeDao.getAllNoticeList(page1);
        map.put("total",page1.getTotal());
        map.put("rows",notices);
        return map;
    }

    @Override
    public int addNotice(Notice notice) {
        return noticeDao.addNotice(notice);
    }

    @Override
    public Notice getNoticeByNoticeId(String noticeId) {
        return noticeDao.getNoticeByNoticeId(noticeId);
    }

    @Override
    public int deleteNoticeById(String noticeId) {
        return noticeDao.deleteNoticeById(noticeId);
    }

    @Override
    public int updateNotice(Notice notice) {
        return noticeDao.updateNotice(notice);
    }

    @Override
    public List<Notice> getNoticeListByUserType(String noticeBelong) {
        return noticeDao.getNoticeListByUserType(noticeBelong);
    }
}
