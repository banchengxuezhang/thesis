package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jxufe.ljw.thesis.bean.ThesisInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Classname ThesisDap
 * @Author: LeJunWen
 * @Date: 2020/2/29 15:30
 */
@Mapper
public interface ThesisInfoDao {
    int addThesis(ThesisInfo thesisInfo);

    int deleteThesis(String thesisId);

    List<ThesisInfo> getThesisInfo(Pagination page, @Param("thesis") ThesisInfo thesisInfo);

    List<ThesisInfo> getThesisInfoByTeacherNo(Pagination page, String teacherNo);

    int updateThesis(ThesisInfo thesisInfo);

    ThesisInfo getThesisByThesisId(String thesisId);
    int getThesisNum(int selectNum);
}
