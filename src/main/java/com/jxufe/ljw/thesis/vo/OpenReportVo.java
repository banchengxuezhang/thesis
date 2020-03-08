package com.jxufe.ljw.thesis.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Classname OpenReportVo
 * @Author: LeJunWen
 * @Date: 2020/3/8 16:50
 */
@Data
public class OpenReportVo{
    private MultipartFile file;
    private  String openReportId;
    private  String thesisNo;
    private  String openReportSummary;
    private String openReportWay;
    private String openReportUrl;
    private String reviewUrl;
    private String reviewContent;
    private String inspectionPass;
    private String inspectionNoPass;

}
