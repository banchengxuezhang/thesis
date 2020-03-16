package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GroupService groupService;
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
            TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(studentTeacherRelation.getTeacherNo());
            if(teacherInfo.getGroupName()!=""&&!StringUtils.isEmpty(teacherInfo.getGroupName())){
                Group group=groupService.getGroupByGroupName(teacherInfo.getGroupName());
                if(group.getGroupStatus()==1){
                    BeanUtils.copyProperties(group,studentTeacherRelation);
                }
            }

            return ClassUtil.checkNull(studentTeacherRelation);
        }catch (Exception e){
            return ResultUtil.success("并无相关信息！");
        }
    }
    @GetMapping("/getReplyScore")
    public Object getReplyScore(String thesisNo){
        ReplyScore replyScore=replyScoreService.getReplyScoreByThesisNo(thesisNo);
            return replyScore;
    }
    @PostMapping("/updateScoreList")
    public Object updateScoreList(ReplyScore replyScore){
        logger.info("查看分数信息！！！"+replyScore);
        replyScoreService.updateReplyScore(replyScore);
        return ResultUtil.success("提交分数信息成功！！！");
    }
    @PostMapping("/updateCheckStatus")
    public Object updateCheckStatus(String thesisNo){
      ReplyScore replyScore=new ReplyScore();
      StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(thesisNo);
      studentService.updateStudentInfo(studentTeacherRelation.getStudentNo(),"","", PublicData.finish);
      replyScore.setThesisNo(thesisNo);
      replyScore.setCheckStatus(1);
     int flag= replyScoreService.updateReplyScore(replyScore);
      if(flag>0){
          return ResultUtil.success("成功验收！！！");
      }else {
          return ResultUtil.success("验收失败，请检查您是否有未完成的操作！！！");
      }
    }
}
