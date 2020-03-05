package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname NoticeDao
 * @Author: LeJunWen
 * @Date: 2020/3/4 22:11
 */
@Mapper
public interface NoticeDao {
    List<Notice> getAllNoticeList();
}
