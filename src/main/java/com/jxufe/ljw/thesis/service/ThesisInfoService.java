package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.bean.ThesisInfo;

import java.util.List;
import java.util.Map;

/**
 * @Classname ThesisInfo
 * @Author: LeJunWen
 * @Date: 2020/2/29 15:42
 */
public interface ThesisInfoService {
    int addThesisInfo(ThesisInfo thesisInfo);

    int deleteThesisInfo(String thesisId);

    Map<String, Object> getThesisInfo(int page, int rows, ThesisInfo thesisInfo);

    Map<String, Object> getThesisInfoByTeacherNo(int page, int rows, String teacherNo);

    int updateThesis(ThesisInfo thesisInfo);

    ThesisInfo getThesisByThesisId(String thesisId);
    int getThesisNum(int selectNum);
    int deleteThesisByAccount(String userAccount);
}
