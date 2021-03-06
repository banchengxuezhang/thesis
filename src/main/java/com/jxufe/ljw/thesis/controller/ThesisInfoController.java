package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Classname ThesisController
 * @Author: LeJunWen
 * @Date: 2020/2/29 15:26
 */
@RestController
public class ThesisInfoController {
    private static final Logger logger = LoggerFactory.getLogger(ThesisInfoController.class);
    @Autowired
    private InitService initService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private ThesisInfoService thesisInfoService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 教师发布新论文课题
     *
     * @param thesisInfo 论文课题信息
     * @param request    请求
     * @return
     */
    @Transactional
    @PostMapping("/addThesis")
    public Object addThesisInfo(ThesisInfo thesisInfo, HttpServletRequest request) {
        try {
            String id = "THESIS" + System.currentTimeMillis();
            User user = (User) request.getSession().getAttribute("user");
            TeacherInfo teacherInfo = teacherService.getTeacherInfo(user.getUserId());
            thesisInfo.setTeacherNo(teacherInfo.getTeacherNo());
            thesisInfo.setTeacherName(teacherInfo.getTeacherName());
            thesisInfo.setThesisId(id);
            thesisInfo.setSelectNum(0);
            thesisInfoService.addThesisInfo(thesisInfo);

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("添加论文课题异常！！！");

        }
        return ResultUtil.success("添加论文课题成功！！！");
    }

    /**
     * 教师更新论文课题信息
     *
     * @param thesisInfo 论文课题信息
     * @return
     */
    @Transactional
    @PostMapping("/updateThesis")
    public Object updateThesis(ThesisInfo thesisInfo) {
        logger.info("进行更新论文过程！,数据为：" + thesisInfo);
        try {
            thesisInfoService.updateThesis(thesisInfo);
            StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisInfo.getThesisId());
            if (studentTeacherRelation != null) {
                if ((!studentTeacherRelation.getStudentEmail().isEmpty()) && studentTeacherRelation.getStudentEmail() != "") {
                    iMailService.sendSimpleMail(studentTeacherRelation.getStudentEmail(), "论文课题已被更新！请查看详情！", studentTeacherRelation.getStudentName() + "同学您好！\n您选择的论文课题已经被" + studentTeacherRelation.getTeacherName() + "教师更新了，如果有什么问题，请您及时联系您的导师！\n");
                }
            }
            return ResultUtil.success("修改论文课题成功！！！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("修改论文课题异常！！！");
        }
    }

    /**
     * 教师删除论文信息，同时提醒学生
     *
     * @param thesisIds 论文id列表
     * @return
     */
    @Transactional
    @DeleteMapping("/deleteThesisInfoByThesisIds")
    public Object deleteThesisInfo(String[] thesisIds) {
        try {
            for (String thesisId : thesisIds
            ) {
                thesisInfoService.deleteThesisInfo(thesisId);
                StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisId);
                if (studentTeacherRelation != null) {
                    if ((!studentTeacherRelation.getStudentEmail().isEmpty()) && studentTeacherRelation.getStudentEmail() != "") {
                        studentService.updateStudentInfo(studentTeacherRelation.getStudentNo(),"","", PublicData.waitSelectThesis);
                        iMailService.sendSimpleMail(studentTeacherRelation.getStudentEmail(), "论文课题已被删除！请查看详情！", studentTeacherRelation.getStudentName() + "同学您好！很抱歉！\n您选择的论文课题《"+studentTeacherRelation.getThesisTitle()+"》已经被" + studentTeacherRelation.getTeacherName() + "教师删除了，请您及时选择其它论文课题！\n");

                    }
                }
                relationService.deleteRelationByThesisNo(thesisId);
            }
            return ResultUtil.success("删除论文成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("删除论文异常！");
        }

    }

    /**
     * 学生获取论文信息
     *
     * @param page       页码
     * @param rows       条数
     * @param thesisInfo 条件
     * @return
     */
    @GetMapping("/getThesisInfo")
    public Object getThesisInfo(int page, int rows, ThesisInfo thesisInfo) {
        try {
            logger.info("查看选择条件ThesisInfo：" + thesisInfo);
            Map<String, Object> res = thesisInfoService.getThesisInfo(page, rows, thesisInfo);
            logger.info("==================查询论文信息结束，数据：{}==================", res);
            return res;
        } catch (Exception e) {
            logger.info("获取论文题目列表失败，失败原因e={}。", e);
        }
        return ResultUtil.dataGridEmptyResult();
    }

    /**
     * 修改时获取论文信息
     *
     * @param thesisId 论文id
     * @return
     */
    @GetMapping("/getThesisInfoByThesisId")
    public Object getThesisByThesisId(String thesisId) {
        try {
            logger.info("获取论文Id:" + thesisId + "获取论文信息" + thesisInfoService.getThesisByThesisId(thesisId));
            return ResultUtil.success(thesisInfoService.getThesisByThesisId(thesisId));
        } catch (Exception e) {
            return ResultUtil.error("获取论文题目出错！！！");

        }
    }

    /**
     * 教师获取自己的论文列表
     *
     * @param page    页码
     * @param rows    条数
     * @param request 请求
     * @return
     */
    @GetMapping("/getThesisInfoByTeacherNo")
    public Object getThesisInfoByTeacherNo(int page, int rows, HttpServletRequest request) {
        try {
            HttpSession session= request.getSession();
            User user = (User) session.getAttribute("user");
            Map<String, Object> res = thesisInfoService.getThesisInfoByTeacherNo(page, rows, user.getUserAccount());
            logger.info("==================查询论文信息结束，数据：{}==================", res);
            return res;
        } catch (Exception e) {
            logger.info("获取论文题目列表失败，失败原因e={}。", e);
        }
        return ResultUtil.dataGridEmptyResult();
    }

    /**
     * 教师判断自己能否再出题
     * @param request
     * @return
     */
    @GetMapping("/getPowerGiveThesis")
    public Object getPowerGiveThesis(HttpServletRequest request){
        try {
            User user= (User) request.getSession().getAttribute("user");
            Map<String, Object> res = thesisInfoService.getThesisInfoByTeacherNo(1, Integer.MAX_VALUE, user.getUserAccount());
            Init init=initService.getInitInfo();
            if(((List)res.get("rows")).size()>=init.getTeacherGive()){
                return ResultUtil.error("已达到最大出题数目！！！");
            }
        }catch (Exception e){
            logger.info("获取题目数量出错！");
        }
       return ResultUtil.success("允许进入！");
    }
}
