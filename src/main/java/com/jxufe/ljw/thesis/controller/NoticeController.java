package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname NoticeController
 * @Author: LeJunWen
 * @Date: 2020/3/3 18:44
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ThesisInfoService thesisInfoService;

    @GetMapping("/getNoticeInfo")
    public Object getNoticeInfo(HttpServletRequest request) {
        try{

            User user = (User) request.getSession().getAttribute("user");
            List<Menu> list = menuService.getMenuByStatus(1);
            Map<String, Object> map = new HashMap<>();
            StringBuilder stringBuilder = new StringBuilder("");
            String nowStage = "评分阶段";
            String text1 = "";
            String text2 = "";
            for (Menu menu : list
            ) {
                stringBuilder.append(menu.getMenuText());
            }
            if (stringBuilder.indexOf("学生选题") != -1 && stringBuilder.indexOf("给定题目") != -1) {
                nowStage = "选题阶段";
            } else if (stringBuilder.indexOf("上传任务书") != -1 && stringBuilder.indexOf("下载任务书") != -1) {
                nowStage = "指导阶段";
            } else if (stringBuilder.indexOf("答辩") != -1) {
                nowStage = "答辩阶段";
            }
            map.put("nowStage", nowStage);
            if (user.getUserType() == UserType.STUDENT.getType()) {
                map.put("userType","3");
                StudentInfo studentInfo=studentService.getStudentInfoByStudentNo(user.getUserAccount());
                //剩余课程数
                text1 =String.valueOf(thesisInfoService.getThesisNum(0));
                //当前状态
                text2=studentInfo.getStudentStage();
            }
            if(user.getUserType()==UserType.TEACHER.getType()){
                map.put("userType","2");
                //已选课题数
                Map<String,Object> map1=thesisInfoService.getThesisInfoByTeacherNo(1,Integer.MAX_VALUE,user.getUserAccount());
                List<ThesisInfo> list1= (List<ThesisInfo>) map1.get("rows");
                int sum=0;
                for (ThesisInfo t:list1
                ) {
                    if(t.getSelectNum()==1){
                        sum+=1;
                    }
                }
                text1=String.valueOf(sum);
                //待确认人数
                Map<String,Object> map2= (Map<String, Object>) relationService.getStudentSelectThesisByTeacherNo(1,Integer.MAX_VALUE,user.getUserAccount(),0);
                text2=((List)map2.get("rows")).size()+"";
            }
            if(user.getUserType()==UserType.MANAGE.getType()){
                map.put("userType","1");
                //未选题学生数
                text1=String.valueOf(commonService.getNoThesisStudentNum());
                //未带学生教师数
                text2=String.valueOf(commonService.getNoStudentTeacherNum());
            }
            map.put("text1",text1);
            map.put("text2",text2);
            return ResultUtil.success(map);
        }catch (Exception e){
            return ResultUtil.error("加载首页信息异常！");
        }
    }
}
