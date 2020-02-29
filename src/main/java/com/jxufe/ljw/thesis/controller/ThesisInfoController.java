package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.bean.ThesisInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.service.ThesisInfoService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private static final Logger logger= LoggerFactory.getLogger(ThesisInfoController.class);
    @Autowired
    private ThesisInfoService thesisInfoService;
    @Autowired
    private TeacherService teacherService;
    @PostMapping("/addThesis")
    public Object addThesisInfo(ThesisInfo thesisInfo, HttpServletRequest request){
        try {
            String id="THESIS"+System.currentTimeMillis();
            User user = (User) request.getSession().getAttribute("user");
            TeacherInfo teacherInfo=teacherService.getTeacherInfo(user.getUserId());
            thesisInfo.setTeacherNo(teacherInfo.getTeacherNo());
            thesisInfo.setTeacherName(teacherInfo.getTeacherName());
            thesisInfo.setThesisId(id);
            thesisInfo.setSelectNum(0);
            thesisInfoService.addThesisInfo(thesisInfo);

        }catch (Exception e){
            return ResultUtil.error("添加论文课题异常！！！");

        }
        return ResultUtil.success("添加论文课题成功！！！");
    }
    @PostMapping("/updateThesis")
    public Object updateThesis(ThesisInfo thesisInfo){
        logger.info("进行更新论文过程！,数据为："+thesisInfo);
        try{
            thesisInfoService.updateThesis(thesisInfo);
            return ResultUtil.success("修改论文课题成功！！！");
        }catch (Exception e){
            return ResultUtil.error("修改论文课题异常！！！");
        }
    }
    @DeleteMapping("/deleteThesisInfo")
    public Object deleteThesisInfo(String[] thesisIds){
        try{
            //删除论文信息，待完善
           thesisInfoService.deleteThesisInfo(Arrays.asList(thesisIds));
            return ResultUtil.success("删除论文成功！");
        }catch (Exception e){
            return ResultUtil.error("删除论文异常！");
        }

    }
    @GetMapping("/getThesisInfo")
    public Object getThesisInfo(int page,int rows,String thesisTitle,String teacherNo,String teacherName){
        try {
            ThesisInfo thesisInfo=new ThesisInfo();
            thesisInfo.setThesisTitle(thesisTitle);
            thesisInfo.setTeacherName(teacherName);
            thesisInfo.setTeacherNo(teacherNo);
            Map<String, Object> res = thesisInfoService.getThesisInfo(page, rows,thesisInfo);
            logger.info("==================查询论文信息结束，数据：{}==================", res);
            return res;
        }catch (Exception e){
            logger.info("获取论文题目列表失败，失败原因e={}。",e);
        }
        return ResultUtil.dataGridEmptyResult();
    }
    @GetMapping("/getThesisInfoByThesisId")
    public Object getThesisByThesisId(String thesisId){
      try{
          logger.info("获取论文Id:"+thesisId+"获取论文信息"+thesisInfoService.getThesisByThesisId(thesisId));
          return ResultUtil.success(thesisInfoService.getThesisByThesisId(thesisId));
      }catch (Exception e){
          return ResultUtil.error("获取论文题目出错！！！");

      }
    }
    @GetMapping("/getThesisInfoByTeacherNo")
    public Object getThesisInfoByTeacherNo(int page,int rows,HttpServletRequest request){
        try{
            User user= (User) request.getSession().getAttribute("user");
            Map<String,Object> res=thesisInfoService.getThesisInfoByTeacherNo(page,rows,user.getUserAccount());
            logger.info("==================查询论文信息结束，数据：{}==================", res);
            return res;
        }catch (Exception e){
            logger.info("获取论文题目列表失败，失败原因e={}。",e);
        }
        return ResultUtil.dataGridEmptyResult();
    }
}
