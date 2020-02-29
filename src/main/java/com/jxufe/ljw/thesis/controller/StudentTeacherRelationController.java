package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.service.InitService;
import com.jxufe.ljw.thesis.service.StudentService;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.service.ThesisInfoService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StudentService studentService;
    @Autowired
    private ThesisInfoService thesisInfoService;
    @Autowired
    private InitService initService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @PostMapping("/getStudentSelectThesisResult")
    public Object getStudentSelectThesisResult(String thesisId, HttpServletRequest request){
        logger.info("进入论文题目关系建立接口，thesisId："+thesisId);
        User user= (User) request.getSession().getAttribute("user");
        StudentInfo studentInfo=studentService.getStudentInfo(user.getUserId());
        Init init = initService.getInitInfo();
        int studentNum = init.getStudentNum();
        List<StudentTeacherRelation> res=relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
        if(res.size()>=studentNum){
            return  ResultUtil.success("选题数已经达到最大值，不能再选！");
        }
        ThesisInfo thesisInfo=thesisInfoService.getThesisByThesisId(thesisId);
        if(thesisInfo.getSelectNum()>0){
            return ResultUtil.success("该选题已被选,请选择其他选题！");
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
        relationService.addStudentTeacherRelation(studentTeacherRelation);
        thesisInfo.setSelectNum(thesisInfo.getSelectNum()+1);
        logger.info("查看ThesisInfo："+thesisInfo);
        thesisInfoService.updateThesis(thesisInfo);
        return ResultUtil.success("选题成功");

    }
}
