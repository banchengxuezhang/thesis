package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.dao.CommonDao;
import com.jxufe.ljw.thesis.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname CommonService
 * @Author: LeJunWen
 * @Date: 2020/3/3 23:43
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonDao commonDao;
    @Override
    public int getNoThesisStudentNum() {
        return commonDao.getNoThesisStudentNum();
    }

    @Override
    public int getNoStudentTeacherNum() {
        return commonDao.getNoStudentTeacherNum();
    }
}
