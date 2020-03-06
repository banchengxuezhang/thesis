package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.NoPassThesis;
import com.jxufe.ljw.thesis.dao.NoPassThesisDao;
import com.jxufe.ljw.thesis.service.NoPassThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname NoPassThesisServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/3/3 13:23
 */
@Service("noPassThesisService")
public class NoPassThesisServiceImpl implements NoPassThesisService {
    @Autowired
    private NoPassThesisDao noPassThesisDao;

    @Override
    public int addNoPassThesis(NoPassThesis noPassThesis) {
        return noPassThesisDao.addNoPassThesis(noPassThesis);
    }

    @Override
    public List<NoPassThesis> getNoPassThesisByStudentNo(String studentNo) {
        return noPassThesisDao.getNoPassThesisByStudentNo(studentNo);
    }

    @Override
    public int getNoPassNum() {
        return noPassThesisDao.getNoPassNum();
    }

    @Override
    public int deleteNoPassByStudentNoOrTeacherNo(String userAccount) {
        return noPassThesisDao.deleteNoPassByStudentNoOrTeacherNo(userAccount);
    }
}
