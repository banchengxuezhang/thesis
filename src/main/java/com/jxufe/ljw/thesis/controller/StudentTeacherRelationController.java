package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname StudentTeacherRelationController
 * @Author: LeJunWen
 * @Date: 2020/2/29 20:19
 */
@RestController
@RequestMapping("/studentTeacherRelation")
public class StudentTeacherRelationController {
    private static final Logger logger= LoggerFactory.getLogger(StudentTeacherRelationController.class);
    @Autowired
    private IMailService iMailService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ThesisInfoService thesisInfoService;
    @Autowired
    private InitService initService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @PostMapping("/getStudentSelectThesisResult")
    public Object getStudentSelectThesisResult(String thesisId, HttpServletRequest request){
      try{
          logger.info("进入论文题目关系建立接口，thesisId："+thesisId);
          User user= (User) request.getSession().getAttribute("user");
          StudentInfo studentInfo=studentService.getStudentInfo(user.getUserId());
          Init init = initService.getInitInfo();
          int studentNum = init.getStudentNum();
          List<StudentTeacherRelation> res=relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
          if(res.size() >=studentNum){
              return  ResultUtil.success("选题数已经达到最大值，不能再选！");
          }
          ThesisInfo thesisInfo=thesisInfoService.getThesisByThesisId(thesisId);
          if(thesisInfo.getSelectNum()>0){
              return ResultUtil.success("该选题已被选,请选择其他选题！");
          }
          if(relationService.getStudentSelectThesisAgreeNumByTeacherNo(thesisInfo.getTeacherNo(),0)+relationService.getStudentSelectThesisAgreeNumByTeacherNo(thesisInfo.getTeacherNo(),1)>=init.getTeacherNum()){
              return ResultUtil.success("该老师名额已经被选满了,请选择其他老师选题！");
          }
          StudentTeacherRelation studentTeacherRelation=new StudentTeacherRelation();
          String id="RELATION"+System.currentTimeMillis();
          studentTeacherRelation.setRelationId(id);
          studentTeacherRelation.setStudentNo(studentInfo.getStudentNo());
          studentTeacherRelation.setStudentName(studentInfo.getStudentName());
          studentTeacherRelation.setStudentClass(studentInfo.getStudentClass());
          studentTeacherRelation.setTeacherNo(thesisInfo.getTeacherNo());
          studentTeacherRelation.setTeacherName(thesisInfo.getTeacherName());
          studentTeacherRelation.setThesisNo(thesisInfo.getThesisId());
          studentTeacherRelation.setThesisTitle(thesisInfo.getThesisTitle());
          studentTeacherRelation.setOpinionFlag(0);
          studentTeacherRelation.setTaskStatus(2);
          studentTeacherRelation.setThesisStatus(2);
          TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(thesisInfo.getTeacherNo());
          if(!(teacherInfo.getTeacherEmail().isEmpty())&&teacherInfo.getTeacherEmail()!=""){
              iMailService.sendSimpleMail(teacherInfo.getTeacherEmail(),"论文课题已被选择！请查看详情！",teacherInfo.getTeacherName()+"教师您好！\n您的论文课题《"+studentTeacherRelation.getThesisTitle()+"》已经被"+studentTeacherRelation.getStudentClass()+"的"+studentTeacherRelation.getStudentName()+"同学申请了，请您及时处理，超过选题阶段时限后，系统将自动为您同意！");
          }
          relationService.addStudentTeacherRelation(studentTeacherRelation);
          thesisInfo.setSelectNum(thesisInfo.getSelectNum()+1);
          logger.info("查看ThesisInfo："+thesisInfo);
          thesisInfoService.updateThesis(thesisInfo);
          return ResultUtil.success("选题成功！！！");
      }catch (Exception e){
          return ResultUtil.error("选题异常！！！");
      }

    }
    @GetMapping("/getStudentTeacherRelationByStudentNo")
    public Object getStudentTeacherRelationByStudentNo(HttpServletRequest request){
        try{
            User user= (User) request.getSession().getAttribute("user");
            logger.info("获取学生no"+user.getUserAccount());
            List<StudentTeacherRelation> studentTeacherRelations=relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
            for (StudentTeacherRelation studentTeacherRelation:studentTeacherRelations) {
                if(studentTeacherRelation.getOpinionFlag()==0){
                    studentTeacherRelation.setTeacherOpinion("未给出");
                    studentTeacherRelation.setOpinionFlagStr("审核中");
                }
                else {
                    if(studentTeacherRelation.getTeacherOpinion().isEmpty()||studentTeacherRelation.getTeacherOpinion()==""){
                        studentTeacherRelation.setTeacherOpinion("无");
                    }
                    if(studentTeacherRelation.getOpinionFlag()==1){
                        studentTeacherRelation.setOpinionFlagStr("同意");
                    }
                     if (studentTeacherRelation.getOpinionFlag()==2){
                        studentTeacherRelation.setOpinionFlagStr("拒绝");
                    }
                }
            }
            return ResultUtil.success(studentTeacherRelations);
        }catch (Exception e){
           return ResultUtil.error("获取选题情况异常！");
        }
    }
    @GetMapping("/getStudentSelectThesisByTeacherNo")
    public Object getStudentSelectThesisByTeacherNo(int page,int rows,HttpServletRequest request){
        try{
            User user= (User) request.getSession().getAttribute("user");
            return relationService.getStudentSelectThesisByTeacherNo(page,rows,user.getUserAccount());
        }catch (Exception e){
            return ResultUtil.error("获取选题学生列表异常！！！");

        }
    }
    @PostMapping("/operateStudent")
    public Object operateStudent(StudentTeacherRelation studentTeacherRelation,HttpServletRequest request){
        try {
            User user= (User) request.getSession().getAttribute("user");
            StudentTeacherRelation relation=relationService.getStudentTeacherRelationByThesisNo(studentTeacherRelation.getThesisNo());
            relation.setTeacherOpinion(studentTeacherRelation.getTeacherOpinion());
            relation.setOpinionFlag(studentTeacherRelation.getOpinionFlag());

            if(relation.getTeacherOpinion().isEmpty()||relation.getTeacherOpinion()==""){
            relation.setTeacherOpinion("教师没有留下什么话！");
        }
        if(relation.getOpinionFlag()==1){
            Init init = initService.getInitInfo();
            if(relationService.getStudentSelectThesisAgreeNumByTeacherNo(user.getUserAccount(),1)>=init.getTeacherNum()){
                return ResultUtil.success("您已选满了"+init.getTeacherNum()+"名学生，无法同意该申请!");
            }
            iMailService.sendSimpleMail(relation.getStudentEmail(),"论文课题选题成功！请查看详情！",relation.getStudentName()+"同学您好！恭喜你！\n您选择的论文课题《"+relation.getThesisTitle()+"》被"+relation.getTeacherName()+"教师同意了，教师给的留言是：\n"+relation.getTeacherOpinion());

        }
        relationService.operateStudent(relation);
            if(relation.getOpinionFlag()==2){
            ThesisInfo thesisInfo=thesisInfoService.getThesisByThesisId(relation.getThesisNo());
            thesisInfo.setSelectNum(0);
            thesisInfoService.updateThesis(thesisInfo);
            relationService.deleteRelationByThesisNo(relation.getThesisNo());
            if((!relation.getStudentEmail().isEmpty())&&relation.getStudentEmail()!=""){
                logger.info("进入删除邮件发送中心！打印信息："+relation);
                iMailService.sendSimpleMail(relation.getStudentEmail(),"论文课题选题失败！请查看详情！",relation.getStudentName()+"同学您好！\n您选择的论文课题题《"+relation.getThesisTitle()+"》被"+relation.getTeacherName()+"教师拒绝了，教师给的留言是：\n"+relation.getTeacherOpinion());
            }
            }
        return ResultUtil.success("处理成功！！！");
    }catch (Exception e){
        return ResultUtil.error("处理异常！！！");
    }
    }
    @GetMapping("/getAgreeThesisByTeacherNo")
    public Object getAgreeThesisByTeacherNo(int page,int rows,HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        return relationService.getAgreeThesisByTeacherNo(page,rows,user.getUserAccount());
    }
    @PostMapping("/getAgreeThesisByStudentNo")
    public Object getAgreeThesisByStudentNo(int page,int rows,HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        return relationService.getAgreeThesisByStudentNo(page,rows,user.getUserAccount(),1);
    }
}
