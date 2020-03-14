package com.jxufe.ljw.thesis.controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.jxufe.ljw.thesis.bean.Group;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.service.GroupService;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.util.VerifyCodeUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.Addressing;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname HelloController
 * @Author: LeJunWen
 * @Date: 2020/2/24 21:41
 */
@RestController
public class UtilController {
    private static final Logger logger = LoggerFactory.getLogger(UtilController.class);
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentTeacherRelationService relationService;
    /**
     * 验证码生成工具
     *
     * @param response
     * @param session
     */
    @GetMapping("/logincode")
    public void testCode(HttpServletResponse response,HttpSession session){
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            String text = VerifyCodeUtils.outputVerifyImage(130,50,os,4);
            session.setAttribute("loginCode", text);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }


    }
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        //接收参数

        //查询数据，实际可通过传过来的参数当条件去数据库查询，在此我就用空集合（数据）来替代
        //创建poi导出数据对象
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //创建sheet页
        SXSSFSheet sheet = sxssfWorkbook.createSheet("答辩分组");

        CellRangeAddress region1 = new CellRangeAddress(0, 1, (short) 0, (short) 12);
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        sheet.addMergedRegion(region1);
        SXSSFRow headTitle = sheet.createRow(0);
        headTitle.createCell(0).setCellValue("答辩分组明细表");

        //创建表头
        SXSSFRow headRow = sheet.createRow(4);
        //设置表头信息
        headRow.createCell(0).setCellValue("序号");
        headRow.createCell(1).setCellValue("学号");
        headRow.createCell(2).setCellValue("姓名");

        headRow.createCell(3).setCellValue("专业");
        headRow.createCell(4).setCellValue("班级");
        headRow.createCell(5).setCellValue("答辩时间");

        headRow.createCell(6).setCellValue("答辩地点");
        headRow.createCell(7).setCellValue("指导教师");
        headRow.createCell(8).setCellValue("小组组长");
        headRow.createCell(9).setCellValue("答辩组名");
        List<Group> groupList=groupService.getGroupNames();
        int k=1;
        for (Group g:groupList
             ) {
            List<TeacherInfo> teacherInfoList=teacherService.getTeacherByGroupName(g.getGroupName());
            for(TeacherInfo t:teacherInfoList){
                List<StudentTeacherRelation> studentTeacherRelations=relationService.getStudentAgreeByTeacherNo(t.getTeacherNo());
                for (StudentTeacherRelation s:studentTeacherRelations
                     ) {
                    BeanUtils.copyProperties(t,s);
                    BeanUtils.copyProperties(g,s);
                    SXSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell(0).setCellValue(k);
                    dataRow.createCell(1).setCellValue(s.getStudentNo());
                    dataRow.createCell(2).setCellValue(s.getStudentName());
                    dataRow.createCell(3).setCellValue(s.getStudentMajor());
                    dataRow.createCell(4).setCellValue(s.getStudentClass());
                    if(s.getReplyDate()!=null){
                        dataRow.createCell(5).setCellValue(formatter.format(s.getReplyDate()));
                    }else {
                        dataRow.createCell(5).setCellValue(s.getReplyDate());
                    }

                    dataRow.createCell(6).setCellValue(s.getReplyPlace());
                    dataRow.createCell(7).setCellValue(s.getTeacherName());
                    dataRow.createCell(8).setCellValue(s.getGrouperName());
                    dataRow.createCell(9).setCellValue(s.getGroupName());
                    k++;
                }
            }
        }

        // 下载导出
        String filename = "本科毕业生答辩分组表";
        // 设置头信息
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        //一定要设置成xlsx格式
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename + ".xlsx", "UTF-8"));
        //创建一个输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //写入数据
        sxssfWorkbook.write(outputStream);

        // 关闭
        outputStream.close();
        sxssfWorkbook.close();
    }
}
