package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.OpenReport;

/**
 * @Classname OpenREportService
 * @Author: LeJunWen
 * @Date: 2020/3/8 15:49
 */
public interface OpenReportService {
    OpenReport getOpenReportByThesisNo(String thesisNo);
    int addOpenReport(OpenReport openReport);
    int updateOpenReport(OpenReport openReport);
}
