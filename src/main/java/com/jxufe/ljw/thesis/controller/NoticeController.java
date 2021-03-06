package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.*;
import com.jxufe.ljw.thesis.enumeration.PublicData;
import com.jxufe.ljw.thesis.enumeration.UserType;
import com.jxufe.ljw.thesis.service.*;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname NoticeController
 * @Author: LeJunWen
 * @Date: 2020/3/3 18:44
 */
@RestController
@RequestMapping("/notice")
public class
NoticeController {
    private static final Logger logger= LoggerFactory.getLogger(NoticeController.class);
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private InitService initService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ThesisInfoService thesisInfoService;

    /**
     * 获取首页数据
     * @param request
     * @return
     */
    @GetMapping("/getNoticeInfo")
    public Object getNoticeInfo(HttpServletRequest request) {
        try{

            User user = (User) request.getSession().getAttribute("user");
            Map<String, Object> map = new HashMap<>();
            Init init=initService.getInitInfo();
            List<String> noteList =new ArrayList<>() ;
            String nowStage = "选题阶段";
            String text1 = "";
            String text2 = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            Date nowDate =  sdf.parse(s);
            if(nowDate.after(init.getFirstDate())){
                nowStage="过程阶段";
                List<StudentTeacherRelation>  studentTeacherRelations=relationService.getStudentTeacherRelationByStudentNo(user.getUserAccount());
                if(studentTeacherRelations.size()>0){
                    StudentTeacherRelation studentTeacherRelation=studentTeacherRelations.get(0);
                    if(studentTeacherRelation.getOpinionFlag()==0){
                        studentTeacherRelation.setOpinionFlag(1);
                        studentTeacherRelation.setTeacherOpinion("超时！系统自动同意！");
                        relationService.operateStudent(studentTeacherRelation);
                        studentService.updateStudentInfo(user.getUserAccount(),"","", PublicData.waitReview);
                    }
                }
            }
            map.put("nowStage", nowStage);
            if (user.getUserType() == UserType.STUDENT.getType()) {
                map.put("userType","3");
                StudentInfo studentInfo=studentService.getStudentInfoByStudentNo(user.getUserAccount());
                //剩余课程数
                text1 =String.valueOf(thesisInfoService.getThesisNum(0));
                //当前状态
                text2=studentInfo.getStudentStage();
                noteList= Arrays.asList(init.getNotesForStudent().split("\\|\\|"));

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
                noteList= Arrays.asList(init.getNotesForTeacher().split("\\|\\|"));
            }
            if(user.getUserType()==UserType.MANAGE.getType()){
                map.put("userType","1");
                //未选题学生数
                text1=String.valueOf(commonService.getNoThesisStudentNum());
                //未带学生教师数
                text2=String.valueOf(commonService.getNoStudentTeacherNum());
                noteList= Arrays.asList(init.getNotesForManager().split("\\|\\|"));
            }
            int belong=user.getUserType();
            if(belong==1){
                belong=2;
            }
            map.put("text1",text1);
            map.put("text2",text2);
            map.put("noteList",noteList);
            map.put("noticeList",noticeService.getNoticeListByUserType(String.valueOf(belong)));
            logger.info("查看map"+map);
            return ResultUtil.success(map);
        }catch (Exception e){
            return ResultUtil.error("加载首页信息异常！");
        }
    }

    /**
     * 发布公告
     * @param notice
     * @return
     */
    @PostMapping("/publishNotice")
    public Object publishNotice(Notice notice) {
        try {
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            String nowTime = sdf.format(new Date());
            Date time = sdf.parse( nowTime );
            notice.setCreateTime(time);
            notice.setNoticeId("NOTICE"+System.currentTimeMillis());
            MultipartFile file=notice.getFile();
            String path=PublicData.path+"\\"+"notice"+"\\"+notice.getNoticeId();
            String fileName=file.getOriginalFilename();
            ClassUtil.uploadFile(file,"",path,fileName);
            notice.setNoticeUrl(file.getOriginalFilename());
            noticeService.addNotice((Notice) ClassUtil.checkNull(notice));
            return ResultUtil.success("添加公告成功！");
        }catch (Exception e){
            return ResultUtil.success("添加公告失败!");
        }

    }

    /**
     * 更新公告
     * @param notice
     * @return
     */
    @PostMapping("/updateNotice")
    public Object updateNotice(Notice notice) {
        try {
            MultipartFile file=notice.getFile();
            if(file!=null){
                Notice notice1=noticeService.getNoticeByNoticeId(notice.getNoticeId());
                String url=notice1.getNoticeUrl();
                ClassUtil.uploadFile(file,url,PublicData.path+"\\"+"notice"+"\\"+notice.getNoticeId(),file.getOriginalFilename());
                notice.setNoticeUrl(file.getOriginalFilename());
            }
            SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
            String nowTime = sdf.format(new Date());
            Date time = sdf.parse( nowTime );
            notice.setUpdateTime(time);
            noticeService.updateNotice(notice);
            return ResultUtil.success("修改公告成功！");
        }catch (Exception e){
            logger.info("查看修改公告失败异常:"+e);
            return ResultUtil.success("修改公告失败！");
        }

    }

    /**
     * 用户获取公告详情
     * @param request
     * @return
     */
    @GetMapping("/getNoticeByUserType")
    public Object getNoticeByUserType(HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        List<Notice> notices=noticeService.getNoticeListByUserType(String.valueOf(user.getUserType()));
        return notices;
    }

    /**
     * 管理员获取公告列表
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/getAllNoticeList")
    public Object getAllNoticeList(int page,int rows){
        return noticeService.getAllNoticeList(page,rows);
    }

    /**
     * 管理员删除公告
     * @param noticeIds
     * @return
     */
    @DeleteMapping("/deleteNoticesByIds")
    public Object deleteNoticesByIds(String[] noticeIds){
        for (String noticeId:noticeIds
             ) {
            Notice notice=noticeService.getNoticeByNoticeId(noticeId);
            File file = new File(PublicData.path+"\\notice"+"\\"+notice.getNoticeId());
            ClassUtil.delFile(file);
           noticeService.deleteNoticeById(noticeId);
        }
        return ResultUtil.success("删除成功！！！");
    }

    /**
     * 修改时获取公告信息
     * @param noticeId
     * @return
     */
    @GetMapping("/getNoticeByNoticeId")
    public Object getNoticeByNoticeId(String noticeId){
        return noticeService.getNoticeByNoticeId(noticeId);
    }
}
