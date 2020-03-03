package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.NoPassThesis;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;

import java.util.List;

/**
 * @Classname NoPassThesisService
 * @Author: LeJunWen
 * @Date: 2020/3/3 13:22
 */
public interface NoPassThesisService {
    int addNoPassThesis(NoPassThesis noPassThesis);

    List<NoPassThesis> getNoPassThesisByStudentNo(String studentNo);

    int getNoPassNum();
}
