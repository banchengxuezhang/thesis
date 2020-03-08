package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.OpenReport;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname OpenReportDao
 * @Author: LeJunWen
 * @Date: 2020/3/8 15:03
 */
@Mapper
public interface OpenReportDao {
    OpenReport getOpenReportByThesisNo(String thesisNo);
    int addOpenReport(OpenReport openReport);
    int updateOpenReport(OpenReport openReport);
}
