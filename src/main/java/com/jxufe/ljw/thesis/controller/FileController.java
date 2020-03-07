package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.service.IMailService;
import com.jxufe.ljw.thesis.service.StudentService;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.commons.lang3.StringUtils;
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
            String path="\\TaskBook";
            String thesisNo = request.getParameter("thesisNo");
            String thesisTitle = request.getParameter("thesisTitle");
            StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
            String sysPath = PublicData.path + "\\" + relationService.getStudentTeacherRelationByThesisNo(thesisNo).getStudentNo() +path ;
            String taskUrl=studentTeacherRelation.getTaskUrl();
            String studentNo=studentTeacherRelation.getStudentNo();
            String studentName=studentTeacherRelation.getStudentName();
            if (multipartFile.isEmpty()) {
                return ResultUtil.success("上传失败，请选择文件后上传！");
            }
            if ((!StringUtils.isEmpty(taskUrl))&&taskUrl!="") {
                File file = new File(sysPath, taskUrl);
                file.renameTo(new File(sysPath, taskUrl + ".bak"));
            }
            logger.info("==================开始上传单个文件==================");
            // 获得原始后缀
            String fileName = studentNo + "-" + studentName + "-" + multipartFile.getOriginalFilename();
            try {
                File filePath = new File(sysPath, fileName);
                if (!filePath.getParentFile().exists()) {
                    filePath.getParentFile().mkdirs();
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
                logger.info("文件上传路径：" + filePath);
                out.write(multipartFile.getBytes());
                out.flush();
                out.close();
                File file = new File(sysPath, taskUrl + ".bak");
                if (file.exists()) {
                    file.delete();
                }
            } catch (FileNotFoundException e) {
                logger.info("==================文件上传异常，e={}==================", e);
                File file = new File(sysPath, studentTeacherRelation.getTaskUrl() + ".bak");
                file.renameTo(new File(sysPath, studentTeacherRelation.getTaskUrl()));
            }
            if ((!studentTeacherRelation.getStudentEmail().isEmpty()) && studentTeacherRelation.getStudentEmail() != "") {
                iMailService.sendAttachmentsMail(studentTeacherRelation.getStudentEmail(), "论文导师发布课题任务书！请查看详情！", studentTeacherRelation.getStudentName() + "同学您好！\n您选择的论文课题《" + thesisTitle + "》有了新的动态,教师已下达新的任务书，请前往查看或下载附件！", sysPath, fileName);
            }
                relationService.updateTaskUrlByThesisNo(fileName, thesisNo);
                return ResultUtil.success("任务书上传成功！");

        } catch (Exception e) {
            logger.info("==================文件上传异常，e={}==================", e);
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResultUtil.error("文件上传异常！");


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
        try (
                InputStream inputStream = new FileInputStream(new File(path, filename));
                OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download;charset=UTF-8");
            response.addHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            logger.info("------无法下载任务书-------");
        }
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
            if (multipartFile.isEmpty()) {
                return ResultUtil.success("上传失败，请选择文件后上传！");
            }
            logger.info("==================开始上传单个文件==================");
            // 获得原始后缀
            String thesisNo = request.getParameter("thesisNo");
            String thesisTitle = request.getParameter("thesisTitle");
            String sysPath = PublicData.path + "\\" + relationService.getStudentTeacherRelationByThesisNo(thesisNo).getStudentNo() + "\\Thesis";
            StudentTeacherRelation studentTeacherRelation = relationService.getStudentTeacherRelationByThesisNo(thesisNo);
            if (studentTeacherRelation.getTaskStatus() != 2) {
                File file = new File(sysPath, studentTeacherRelation.getThesisUrl());
                file.renameTo(new File(sysPath, studentTeacherRelation.getThesisUrl() + ".bak"));

            }
            String fileName = studentTeacherRelation.getStudentNo() + "-" + studentTeacherRelation.getStudentName() + "-" + multipartFile.getOriginalFilename();
            try {
                File filePath = new File(sysPath, fileName);    //得到文件路径
                if (!filePath.getParentFile().exists()) {    //判断服务器当前路径文件夹是否存在
                    filePath.getParentFile().mkdirs();    //不存在则创建文件夹
                }
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
                logger.info("文件上传路径：" + filePath);
                out.write(multipartFile.getBytes());
                out.flush();
                out.close();
                relationService.updateThesisUrlByThesisNo(fileName, thesisNo);
                TeacherInfo teacherInfo = teacherService.getTeacherInfoByTeacherNo(studentTeacherRelation.getTeacherNo());
                if ((!teacherInfo.getTeacherEmail().isEmpty()) && teacherInfo.getTeacherEmail() != "") {
                    //  iMailService.sendSimpleMail(teacherInfo.getTeacherEmail(),"学生已上传论文！请查看详情！",teacherInfo.getTeacherName()+"教师您好！\n您发布的论文课题《"+thesisTitle+"》有了新的动态,学生已上传论文，请前往查看！");
                    iMailService.sendAttachmentsMail(teacherInfo.getTeacherEmail(), "学生已上传论文！请查看详情！", teacherInfo.getTeacherName() + "教师您好！\n您发布的论文课题《" + thesisTitle + "》有了新的动态,学生已上传论文，请前往查看或下载附件！", sysPath, fileName);

                }
                File file = new File(sysPath, studentTeacherRelation.getThesisUrl() + ".bak");
                if (file.exists()) {
                    file.delete();
                }
                studentService.updateStudentInfo(studentTeacherRelation.getStudentNo(),"","",PublicData.waitAnswer);
                return ResultUtil.success("论文上传成功！");
            } catch (FileNotFoundException e) {
                File file = new File(sysPath, studentTeacherRelation.getThesisUrl() + ".bak");
                file.renameTo(new File(sysPath, studentTeacherRelation.getThesisUrl()));
                logger.info("==================文件上传异常，e={}==================", e);
            }

        } catch (Exception e) {

            logger.info("==================文件上传异常，e={}==================", e);
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return ResultUtil.error("文件上传异常！");

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
        try (
                InputStream inputStream = new FileInputStream(new File(path, filename));
                OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download;charset=UTF-8");
            response.addHeader("content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            logger.info("------无法下载学生论文-------");
        }
    }
    /**
     * 上传开题报告
     */
    @PostMapping("/uploadOpenReport")
    public Object uploadOpenReport(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        return null;
    }
}

