package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.service.IMailService;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Classname UploadFileController
 * @Author: LeJunWen
 * @Date: 2020/3/1 22:44
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    private static final Logger logger= LoggerFactory.getLogger(FileController.class);
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private IMailService iMailService;
    /**
     * 接收上传的文件，并且将上传的文件存储在指定路径下
     * @param request
     * @return
     */
    @PostMapping("/uploadTask")
    public Object uploadTask(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            if (multipartFile.isEmpty()) {
                return ResultUtil.success("上传失败，请选择文件后上传！");
            }
            logger.info("==================开始上传单个文件==================");
            // 获得原始后缀
            String sysPath= PublicData.PATH.getPath()+"\\TaskBook";
            String thesisNo = request.getParameter("thesisNo");
            String thesisTitle = request.getParameter("thesisTitle");
            String fileName = multipartFile.getOriginalFilename();
            try {
                File filePath=new File(sysPath,fileName);    //得到文件路径
                if(!filePath.getParentFile().exists()){    //判断服务器当前路径文件夹是否存在
                    filePath.getParentFile().mkdirs();    //不存在则创建文件夹
                }
                BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(filePath));
                logger.info("文件上传路径："+filePath);
                out.write(multipartFile.getBytes());
                out.flush();
                out.close();
                relationService.updateTaskUrlByThesisNo(fileName,thesisNo);
                StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(thesisNo);
                if((!studentTeacherRelation.getStudentEmail().isEmpty())&&studentTeacherRelation.getStudentEmail()!=""){
                    iMailService.sendSimpleMail(studentTeacherRelation.getStudentEmail(),"论文导师发布课题任务书！请查看详情！",studentTeacherRelation.getStudentName()+"同学您好！\n您选择的论文课题《"+thesisTitle+"》有了新的动态,教师已下达新的任务书，请前往查看！");
                }
                return  ResultUtil.success("任务书上传成功！");
            }catch (FileNotFoundException e){
               logger.info("==================文件上传异常，e={}==================", e);

            }

        }catch (Exception e){

            logger.info("==================文件上传异常，e={}==================", e);
        }

        return ResultUtil.error("文件上传异常！");

    }
    @PostMapping("/uploadThesis")
    public Object uploadThesis(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            if (multipartFile.isEmpty()) {
                return ResultUtil.success("上传失败，请选择文件后上传！");
            }
            logger.info("==================开始上传单个文件==================");
            // 获得原始后缀
            String sysPath= PublicData.PATH.getPath()+"\\Thesis";
            String thesisNo = request.getParameter("thesisNo");
            String thesisTitle = request.getParameter("thesisTitle");
            String fileName = multipartFile.getOriginalFilename();
            try {
                File filePath=new File(sysPath,fileName);    //得到文件路径
                if(!filePath.getParentFile().exists()){    //判断服务器当前路径文件夹是否存在
                    filePath.getParentFile().mkdirs();    //不存在则创建文件夹
                }
                BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(filePath));
                logger.info("文件上传路径："+filePath);
                out.write(multipartFile.getBytes());
                out.flush();
                out.close();
                relationService.updateThesisUrlByThesisNo(fileName,thesisNo);
                StudentTeacherRelation studentTeacherRelation=relationService.getStudentTeacherRelationByThesisNo(thesisNo);
                TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(studentTeacherRelation.getTeacherNo());
                if((!teacherInfo.getTeacherEmail().isEmpty())&&teacherInfo.getTeacherEmail()!=""){
                    iMailService.sendSimpleMail(teacherInfo.getTeacherEmail(),"学生已上传论文！请查看详情！",teacherInfo.getTeacherName()+"教师您好！\n您发布的论文课题《"+thesisTitle+"》有了新的动态,学生已上传论文，请前往查看！");
                }
                return  ResultUtil.success("任务书上传成功！");
            }catch (FileNotFoundException e){
                logger.info("==================文件上传异常，e={}==================", e);

            }

        }catch (Exception e){

            logger.info("==================文件上传异常，e={}==================", e);
        }

        return ResultUtil.error("文件上传异常！");

    }
}

