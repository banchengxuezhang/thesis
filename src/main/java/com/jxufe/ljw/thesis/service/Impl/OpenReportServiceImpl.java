package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.OpenReport;
import com.jxufe.ljw.thesis.dao.OpenReportDao;
import com.jxufe.ljw.thesis.service.OpenReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname OpenREportServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/3/8 15:49
 */
@Service("openReportService")
public class OpenReportServiceImpl implements OpenReportService {
    @Autowired
    private OpenReportDao reportDao;
    @Override
    public OpenReport getOpenReportByThesisNo(String thesisNo) {
        return reportDao.getOpenReportByThesisNo(thesisNo);
    }

    @Override
    public int addOpenReport(OpenReport openReport) {
        return reportDao.addOpenReport(openReport);
    }

    @Override
    public int updateOpenReport(OpenReport openReport) {
        return reportDao.updateOpenReport(openReport);
    }
}
