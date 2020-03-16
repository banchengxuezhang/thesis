package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.OpenReport;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Classname UploadFileController
 * @Author: LeJunWen
 * @Date: 2020/3/1 22:44
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private OpenReportService openReportService;
    @Autowired
    private NoReplyService noReplyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private IMailService iMailService;

    /**
     * 接收上传的任务书，并存储在指定目录下
     *
     * @param request       请求
     * @param multipartFile 文件
     * @return
     */
    @Transactional
    @PostMapping("/uploadTask")
    public Object uploadTask(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            String thesisNo = request.getParameter("thesisNo");
            StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
            String studentNo=studentTeacherRelation.getStudentNo();
            String studentName=studentTeacherRelation.getStudentName();
            String sysPath = PublicData.path + "\\" + studentNo +"\\TaskBook" ;
            String taskUrl=studentTeacherRelation.getTaskUrl();
            String fileName = studentNo + "-" + studentName + "-" + multipartFile.getOriginalFilename();
           Boolean flag= ClassUtil.uploadFile(multipartFile,taskUrl,sysPath,fileName);
           if(!flag){
               return ResultUtil.success("文件上传失败！请重新上传！");
           }
           else {
               if ((!studentTeacherRelation.getStudentEmail().isEmpty()) && studentTeacherRelation.getStudentEmail() != "") {
                   iMailService.sendAttachmentsMail(studentTeacherRelation.getStudentEmail(), "论文导师发布课题任务书！请查看详情！", studentTeacherRelation.getStudentName() + "同学您好！\n您选择的论文课题《" + studentTeacherRelation.getThesisTitle() + "》有了新的动态,教师已下达新的任务书，请前往查看或下载附件！", sysPath, fileName);
               }
               relationService.updateTaskUrlByThesisNo(fileName, thesisNo);
               return ResultUtil.success("任务书上传成功！");
           }

        } catch (Exception e) {
            logger.info("==================文件上传异常，e={}==================", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.success("文件上传失败！请重新上传！");
        }


    }

    /**
     * 下载任务书
     *
     * @param thesisNo 论文编号
     * @param response
     */
    @GetMapping("/downloadTask")
    public void downloadTask(String thesisNo,HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(thesisNo)){
            User user= (User) request.getSession().getAttribute("user");
            List<StudentTeacherRelation> studentTeacherRelations=relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
            thesisNo=studentTeacherRelations.get(0).getThesisNo();
        }
        StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
        String path = PublicData.path + "\\" + studentTeacherRelation.getStudentNo() + "\\TaskBook";
        String filename = studentTeacherRelation.getTaskUrl();
        //下载
        ClassUtil.downloadFile(response, path, filename, "------无法下载任务书-------");
    }

    /**
     * 接收上传的论文，并存储在指定目录下
     *
     * @param request       请求
     * @param multipartFile 文件
     * @return
     */
    @Transactional
    @PostMapping("/uploadThesis")
    public Object uploadThesis(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            String thesisNo = request.getParameter("thesisNo");
            String thesisTitle = request.getParameter("thesisTitle");
            StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
            studentService.updateStudentInfo(studentTeacherRelation.getStudentNo(),"","",PublicData.waitAnswer);
            String sysPath = PublicData.path + "\\" + studentTeacherRelation.getStudentNo() + "\\Thesis";
            String fileName = studentTeacherRelation.getStudentNo() + "-" + studentTeacherRelation.getStudentName() + "-" + multipartFile.getOriginalFilename();
            String taskUrl=studentTeacherRelation.getThesisUrl();
           Boolean flag= ClassUtil.uploadFile(multipartFile,taskUrl,sysPath,fileName);
           if(!flag){
               return ResultUtil.success("文件上传失败，请重新上传！");
           }else {
               relationService.updateThesisUrlByThesisNo(fileName, thesisNo);
               TeacherInfo teacherInfo = teacherService.getTeacherInfoByTeacherNo(studentTeacherRelation.getTeacherNo());
               if ((!teacherInfo.getTeacherEmail().isEmpty()) && teacherInfo.getTeacherEmail() != "") {
                   iMailService.sendAttachmentsMail(teacherInfo.getTeacherEmail(), "学生已上传论文！请查看详情！", teacherInfo.getTeacherName() + "教师您好！\n您发布的论文课题《" + thesisTitle + "》有了新的动态,学生已上传论文，请前往查看或下载附件！", sysPath, fileName);

               }
               studentService.updateStudentInfo(studentTeacherRelation.getStudentNo(),"","",PublicData.waitAnswer);
               return ResultUtil.success("论文上传成功！");
           }

        } catch (Exception e) {
            logger.info("==================文件上传异常，e={}==================", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.success("文件上传失败！请重新上传！");
        }

    }

    /**
     * 下载论文
     *
     * @param thesisNo 论文编号
     * @param response
     */
    @GetMapping("/downloadThesis")
    public void downloadThesis(String thesisNo, HttpServletResponse response) {
        StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
        String path = PublicData.path+ "\\" + studentTeacherRelation.getStudentNo() + "\\Thesis";
        String filename = studentTeacherRelation.getThesisUrl();
        //下载
        ClassUtil.downloadFile(response, path, filename, "------无法下载学生论文-------");
    }

    /**
     * 下载开题报告文件
     * @param thesisNo
     * @param response
     * @return
     */
    @GetMapping("/downloadOpenReport")
    public void downloadOpenReport(String thesisNo, HttpServletResponse response) {
        logger.info("下载开题报告查看thesisNo" + thesisNo);
        StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
        OpenReport openReport = openReportService.getOpenReportByThesisNo(thesisNo);
        String path = PublicData.path + "\\" + studentTeacherRelation.getStudentNo() + "\\OpenReport";
        String filename = openReport.getOpenReportUrl();
        //下载
        ClassUtil.downloadFile(response, path, filename, "------无法下载开题报告-------");
    }

    /**
     * 下载文献综述文件
     * @param thesisNo
     * @param response
     */
    @GetMapping("/downloadReview")
    public  void downloadReview(String thesisNo, HttpServletResponse response) {
        StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
        OpenReport openReport=openReportService.getOpenReportByThesisNo(thesisNo);
        String path = PublicData.path+ "\\" + studentTeacherRelation.getStudentNo() + "\\Review";
        String filename = openReport.getReviewUrl();
        //下载
        ClassUtil.downloadFile(response, path, filename, "------无法下载文献综述-------");
    }

    /**
     * 下载公共文件
     * @param fileName
     * @param response
     */
    @GetMapping("/downloadFile")
    public void downloadFile(@Param("path") String path,@Param("fileName") String fileName,HttpServletResponse response){
        ClassUtil.downloadFile(response,PublicData.path+"\\"+path,fileName,"--------无法下载模板文件-------");

    }
}

