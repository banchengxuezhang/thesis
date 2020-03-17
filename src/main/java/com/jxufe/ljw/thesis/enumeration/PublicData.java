package com.jxufe.ljw.thesis.enumeration;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @Classname PublicData
 * @Author: LeJunWen
 * @Date: 2020/3/2 00:13
 */
@Component
public class PublicData {

    public  static String path="D:\\毕业设计管理本地数据盘";
    public  static String waitSelectThesis="待选题";
    public static  String waitCheck="待审核";
    public static String waitReview="待提交文献综述";
    public static String waitOpenReport="待提交开题报告";
    public static String waitInspection="待提交中期指导";
    public static String waitUploadThesis="待上传论文";
    public static  String waitAnswer="待答辩";
    public  static String finish="已验收";
    public static  String[] managerController={"updateMenuStatus","/init","addStudent","addTeacher","doc.html","druid","/group","list","updateMenuStatus","publishNotice","updateNotice","getAllNoticeList","deleteNoticesByIds","getNoticeByNoticeId","getEchartData","getUserList","getUserDetailInfoById","getTeacherListForManager","deleteUsersByIds","updateTeacherInfo","updateStudentInfo","noCheckStudentStatus"};
    public static  String[] teacherController={"uploadTask","getStudentSelectThesisByTeacherNo","getAgreeThesisByTeacherNo","getThesisInfoByTeacherNo","addThesis","updateThesis","deleteThesisInfoByThesisIds","operateStudent","getThesisForOpenReportAndReviewList","teacherCheckOpenReport","teacherCheckReview","teacherCheckInspection","teacherCheckNoReply","updateScoreList","updateCheckStatus","getPowerGiveThesis"};


}
