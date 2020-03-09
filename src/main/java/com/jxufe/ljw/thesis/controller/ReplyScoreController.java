package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.service.NoReplyService;
import com.jxufe.ljw.thesis.service.OpenReportService;
import com.jxufe.ljw.thesis.service.ReplyScoreService;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname ReplyScoreController
 * @Author: LeJunWen
 * @Date: 2020/3/9 16:01
 */
@RequestMapping("/replyScore")
@RestController
public class ReplyScoreController {
    private  static final Logger logger=LoggerFactory.getLogger(ReplyScoreController.class);
    @Autowired
    private OpenReportService openReportService;
    @Autowired
    private ReplyScoreService replyScoreService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private NoReplyService noReplyService;
    @GetMapping("/getOpenReportScore")
    public Object getOpenReportScore(HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            List<StudentTeacherRelation> studentTeacherRelations = relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
            StudentTeacherRelation studentTeacherRelation=studentTeacherRelations.get(0);
            String thesisNo = studentTeacherRelation.getThesisNo();
            ReplyScore replyScore = replyScoreService.getReplyScoreByThesisNo(thesisNo);
            OpenReport openReport = openReportService.getOpenReportByThesisNo(thesisNo);
            if(openReport!=null){
                BeanUtils.copyProperties(openReport, studentTeacherRelation);
            }
            NoReply noReply=noReplyService.getNoReplyByThesisNo(thesisNo);
            if(noReply!=null){
                BeanUtils.copyProperties(noReply,studentTeacherRelation);
            }
            if (replyScore == null) {
                studentTeacherRelation.setOpenReportStatus(0);
                studentTeacherRelation.setOpenReportScore(0);
            } else {
                BeanUtils.copyProperties(replyScore, studentTeacherRelation);
            }
            return ClassUtil.checkNull(studentTeacherRelation);
        }catch (Exception e){
            return ResultUtil.success("并无相关信息！");
        }
    }
}
