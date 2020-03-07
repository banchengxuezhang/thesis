package com.jxufe.ljw.thesis.bean;

import lombok.Data;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * @Classname Ktbg
 * @Author: LeJunWen
 * @Date: 2020/3/8 00:12
 */
@Data
public class OpenReport {
    private  String openReportId;
    private  String thesisNo;
    private  String openReportSummary;
    private String openReportWay;
    private String openReportUrl;
    private String reviewUrl;
    private String reviewContent;
}
