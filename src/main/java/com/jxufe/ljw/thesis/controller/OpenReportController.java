package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.vo.OpenReportVo;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname OpenReportController
 * @Author: LeJunWen
 * @Date: 2020/3/8 15:22
 */
@RestController
@RequestMapping("/openReport")
public class OpenReportController {
    private static final Logger logger= LoggerFactory.getLogger(OpenReport.class);
    @Autowired
    private IMailService iMailService;
    @Autowired
    private NoReplyService noReplyService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private OpenReportService openReportService;
    @Autowired
    private ReplyScoreService replyScoreService;
    /**
     * 提交开题报告
     * @param openReportVo
     * @return
     */

    @PostMapping("/uploadOpenReport")
    public Object uploadOpenReport(OpenReportVo openReportVo){
        logger.info("查看data"+openReportVo);
        String openReportSummary=openReportVo.getOpenReportSummary();
        String openReportWay=openReportVo.getOpenReportWay();
        String thesisNo=openReportVo.getThesisNo();
        String fileName="";
        if(openReportVo.getFile()!=null){
         StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(thesisNo);
         Boolean flag=true;
         MultipartFile multipartFile=openReportVo.getFile();
         String sysPath= PublicData.path+"\\"+studentTeacherRelation.getStudentNo()+"\\OpenReport";
         if(multipartFile!=null) {
          fileName = studentTeacherRelation.getStudentNo() + "-" + studentTeacherRelation.getStudentName() + "-" + multipartFile.getOriginalFilename();
         }
         OpenReport openReport=openReportService.getOpenReportByThesisNo(thesisNo);
         if(openReport==null){
             OpenReport openReport1=new OpenReport();
             openReport1.setOpenReportSummary(openReportSummary);
             openReport1.setOpenReportWay(openReportWay);
             openReport1.setOpenReportId("OPENREPORT"+System.currentTimeMillis());
             openReport1.setThesisNo(thesisNo);
             openReport1.setOpenReportUrl(fileName);
             if(multipartFile!=null){
                 flag=ClassUtil.uploadFile(multipartFile,"",sysPath,fileName);
             }
             if(flag){
                 OpenReport openReport2= (OpenReport) ClassUtil.checkNull(openReport1);
                 openReportService.addOpenReport(openReport2);
                 return ResultUtil.success("提交开题报告成功！！！");
             }
         }else {
             openReport.setOpenReportSummary(openReportSummary);
             openReport.setOpenReportWay(openReportWay);
             if(multipartFile!=null){
              flag=ClassUtil.uploadFile(multipartFile,openReport.getOpenReportUrl(),sysPath,fileName);
             }
             openReport.setOpenReportUrl(fileName);
             if(flag){
                 openReportService.updateOpenReport(openReport);
                 return ResultUtil.success("提交开题报告成功！！！");
             }

         }
     }else {




     }
     return ResultUtil.success("提交开题报告失败！！！");

       }

    /**
     * 提交文献综述
      * @param openReportVo
     * @return
     */
    @PostMapping("/uploadReview")
    public Object uploadReview(OpenReportVo openReportVo){
        boolean flag=true;
        String url="";
        String fileName="";
       String reviewContent=openReportVo.getReviewContent();
       StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(openReportVo.getThesisNo());
       OpenReport openReport=openReportService.getOpenReportByThesisNo(openReportVo.getThesisNo());
       if(openReportVo.getFile()!=null){
           if(openReport!=null){
                url=openReport.getReviewUrl();
           }
           fileName = studentTeacherRelation.getStudentNo() + "-" + studentTeacherRelation.getStudentName() + "-" +openReportVo.getFile().getOriginalFilename();
           String sysPath= PublicData.path+"\\"+studentTeacherRelation.getStudentNo()+"\\Review";
           flag=ClassUtil.uploadFile(openReportVo.getFile(),url,sysPath,fileName);
       }
       if(flag){
           if(openReport==null){
               openReport=new OpenReport();
               openReport.setOpenReportId("OPENREPORT"+System.currentTimeMillis());
               openReport.setThesisNo(openReportVo.getThesisNo());
               openReport.setReviewContent(reviewContent);
               openReport.setReviewUrl(fileName);
               openReportService.addOpenReport(openReport);
               return ResultUtil.success("提交文献综述成功！！！");
           }
           openReport.setReviewContent(reviewContent);
           openReport.setReviewUrl(fileName);
           openReportService.updateOpenReport(openReport);
           return ResultUtil.success("提交文献综述成功！！！");
       }
       return ResultUtil.success("提交文献综述失败！！！");

    }

    /**
     * 提交中期检查
     * @param openReportVo
     * @return
     */
    @PostMapping("/uploadInspection")
    public Object uploadInspection(OpenReportVo openReportVo){
        try{
            OpenReport openReport=new OpenReport();
            BeanUtils.copyProperties(openReportVo,openReport);
            if( openReportService.updateOpenReport(openReport)<1){
                return ResultUtil.success("提交失败！请提交文献综述和开题报告后再进行此操作！");
            }
            return ResultUtil.success("提交中期检查成功！！!");
        }catch (Exception e){
            logger.info("异常内容为："+e);
            return ResultUtil.success("提交中期检查失败！！！");
        }

    }

    /**
     * 提交免答辩申请
     * @param noReply
     * @return
     */
    @PostMapping("/uploadNoReply")
    public  Object uploadNoReply(NoReply noReply){
        logger.info("查看noReply:"+noReply);
       NoReply noReply1=noReplyService.getNoReplyByThesisNo(noReply.getThesisNo());
     StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(noReply.getThesisNo());
     TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(studentTeacherRelation.getTeacherNo());
       if(noReply1!=null){
           noReplyService.updateNoReply(noReply);
       }else {
           noReply.setNoReplyId("NOREPLY"+System.currentTimeMillis());
           noReply.setStatus(0);
           noReplyService.addNoReply(noReply);
       }
       if(teacherInfo.getTeacherEmail()!=null&&teacherInfo.getTeacherEmail()!=""&&noReply1==null){
           iMailService.sendSimpleMail(teacherInfo.getTeacherEmail(),"重要！查看免答辩申请！",teacherInfo.getTeacherName()+"教师您好！\n您的学生"+studentTeacherRelation.getStudentName()+
                   "同学就《"+studentTeacherRelation.getThesisTitle()+"》这一论题提交了免答辩申请！\n请您核实信息无误后！再进行审核确认！");
       }
       return ResultUtil.success("提交免答辩申请成功！");
    }
    /**
     * 获取开题报告和文献综述等详情
     * @param request
     * @return
     */
    @GetMapping("/getThesisForOpenReportAndReview")
    public Object getThesisRelationForOpenReport(HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            Map<String,Object> map= (Map<String, Object>) relationService.getAgreeThesisByStudentNo(1,Integer.MAX_VALUE, user.getUserAccount(), 1);
            StudentTeacherRelation studentTeacherRelation= (StudentTeacherRelation) ((List)map.get("rows")).get(0);
            OpenReport openReport=openReportService.getOpenReportByThesisNo(studentTeacherRelation.getThesisNo());
            if(openReport!=null){
                BeanUtils.copyProperties(openReport,studentTeacherRelation);
            }
            NoReply noReply=noReplyService.getNoReplyByThesisNo(studentTeacherRelation.getThesisNo());
           if(noReply!=null){
               BeanUtils.copyProperties(noReply,studentTeacherRelation);
           }
            TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(studentTeacherRelation.getTeacherNo());
            studentTeacherRelation.setTeacherTitle(teacherInfo.getTeacherTitle());
            return ClassUtil.checkNull(studentTeacherRelation);
        } catch (Exception e) {
            return ResultUtil.error("获取信息失败！");
        }
    }

    /**
     * 教师获取数据列表
     * @param page
     * @param rows
     * @param request
     * @return
     */
    @GetMapping("/getThesisForOpenReportAndReviewList")
    public Object getThesisForOpenReportAndReviewList(int page,int rows,HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
       List<StudentTeacherRelation> list=new ArrayList<>();
        Map<String,Object> map= (Map<String, Object>) relationService.getAgreeThesisByTeacherNo(page,rows,user.getUserAccount());
        List<StudentTeacherRelation> studentTeacherRelations= (List<StudentTeacherRelation>) map.get("rows");
        for(StudentTeacherRelation s:studentTeacherRelations){
            String thesisNo=s.getThesisNo();
            OpenReport openReport=openReportService.getOpenReportByThesisNo(thesisNo);
            if(openReport!=null){
                BeanUtils.copyProperties(openReport,s);
            }
            NoReply noReply=noReplyService.getNoReplyByThesisNo(thesisNo);
            if(noReply!=null){
                BeanUtils.copyProperties(noReply,s);
            }
            ReplyScore replyScore=replyScoreService.getReplyScoreByThesisNo(thesisNo);
            if(replyScore!=null){
                BeanUtils.copyProperties(replyScore,s);
            }
            StudentTeacherRelation studentTeacherRelation= (StudentTeacherRelation) ClassUtil.checkNull(s);
            list.add(studentTeacherRelation);
        }
        map.put("rows",list);
        return map;
    }
    @GetMapping("/getThesisOpenReportByThesisNo")
    public Object getThesisOpenReportByThesisNo(String thesisNo){
      try {
          StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(thesisNo);
          OpenReport openReport=openReportService.getOpenReportByThesisNo(thesisNo);
          if(openReport!=null){
              BeanUtils.copyProperties(openReport,studentTeacherRelation);
          }
          return ClassUtil.checkNull(studentTeacherRelation);

      }catch (Exception e){
          return  ResultUtil.success("获取开题报告数据异常！");
      }
    }
}
