package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.Group;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.service.GroupService;
import com.jxufe.ljw.thesis.service.IMailService;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.service.TeacherService;
import com.jxufe.ljw.thesis.util.ClassUtil;
import com.jxufe.ljw.thesis.util.RandomGroup;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Classname GroupController
 * @Author: LeJunWen
 * @Date: 2020/3/13 00:23
 */
@RequestMapping("/group")
@RestController
public class GroupController {
    @Autowired
    private IMailService iMailService;
    @Autowired
    private StudentTeacherRelationService relationService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 管理员添加分组
     * @param userIds 添加教师列表
     * @param group 小组信息
     * @return
     */
    @PostMapping("/addGroup")
    public Object addGroup(String[] userIds, Group group){
        Group group1=groupService.getGroupByGroupName(group.getGroupName());
        if(group1!=null){
            return ResultUtil.error("该分组已存在！！！");
        }
        TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(group.getGrouperNo());
        if(teacherInfo==null){
            return ResultUtil.error("该组长教师不存在！！！");
        }
      boolean flag=Arrays.asList(userIds).contains(teacherInfo.getUserId());
      if(!flag){
          return ResultUtil.error("该组长教师不存在此分组！！！");
      }
       if(group.getGrouperName()!=""&&!StringUtils.isEmpty(group.getGrouperName())){
           if(!teacherInfo.getTeacherName().equals(group.getGrouperName())){
               return ResultUtil.error("组长教师编号和姓名不符！！！");
           }
       }
        group.setGroupId("GROUP"+System.currentTimeMillis());
       List<TeacherInfo> list=new ArrayList<>();
        for (String userId:userIds
             ) {
            TeacherInfo teacherInfo1=teacherService.getTeacherInfo(userId);
            list.add(teacherInfo1);
           if(teacherInfo1!=null){
               if((!StringUtils.isEmpty(teacherInfo1.getGroupName()))&&(!teacherInfo1.getGroupName().equals(""))){
                   return ResultUtil.error("很抱歉，您选了其它分组的教师！！！");
               }
           }
        }
        group.setGrouperName(teacherInfo.getTeacherName());
        groupService.addGroup((Group) ClassUtil.checkNull(group));
        int k=0;
        for (String userId:userIds
             ) {
            if(group.getGroupStatus()==1){
                TeacherInfo teacherInfo1=list.get(k);
                iMailService.sendSimpleMail(teacherInfo1.getTeacherEmail(),"答辩时间已出！",teacherInfo1.getTeacherName()+
                        "教师您好！您参与的答辩时间已出！请前往毕业论文管理系统查看！请及时告知学生答辩时间及地点！");
            }
            teacherService.updateTeacherGroupName(userId,group.getGroupName());
            k++;
        }
        return ResultUtil.success("添加分组成功！！！");
    }

    /**
     * 管理员修改分组信息
     * @param group 分组信息
     * @return
     */
    @PostMapping("/modifyGroup")
    public Object modifyGroup(Group group){
        TeacherInfo teacherInfo=teacherService.getTeacherInfoByTeacherNo(group.getGrouperNo());
        if(teacherInfo==null){
            return ResultUtil.error("该教师不存在！！！");
        }else if(!teacherInfo.getGroupName().equals(group.getGroupName())){
            return ResultUtil.error("该教师不在此分组！！！");
        }
        if(group.getGroupStatus()==1){
           List<TeacherInfo> teacherInfoList=teacherService.getTeacherByGroupName(group.getGroupName());
            for (TeacherInfo t:teacherInfoList
                 ) {
                iMailService.sendSimpleMail(t.getTeacherEmail(),"答辩时间已出！",t.getTeacherName()+
                        "教师您好！您参与的答辩时间已出！请前往毕业论文管理系统查看！请及时告知学生答辩时间及地点！");
            }
        }
        groupService.updateGroupByGroupName(group);
        return  ResultUtil.success("更改分组信息成功！！！");
    }

    /**
     * 管理员删除分组
     * @param groupIds
     * @return
     */
    @DeleteMapping("deleteGroupsByIds")
    public Object deleteGroupsByIds(String[] groupIds){
        for (String groupId:groupIds
             ) {
            Group group=groupService.getGroupById(groupId);
            teacherService.updateGroupNameByGroupName(group.getGroupName());
            groupService.deleteGroupById(groupId);
        }
        return ResultUtil.success("删除分组信息成功！！！");
    }

    /**
     * 管理员获取分组信息
     * @param groupId
     * @return
     */
    @GetMapping("/getGroupById")
    public Object getGroupById(String groupId){
        return groupService.getGroupById(groupId);
    }

    /**
     * 随机分组
     * @param num
     * @return
     */
    @PostMapping("/randomGroup")
    public Object randomGroup(int num){
        try {
            groupService.cleanGroup();
            List<TeacherInfo> list=teacherService.getAllTeachers();
            List<List<TeacherInfo>> listList=RandomGroup.getGroup(list,num);
            int i=1;
            for (List<TeacherInfo> teacherList:listList
            ) {
                Group group=new Group();
                group.setGroupId("GROUP"+System.currentTimeMillis());
                group.setGroupName("答辩"+i+"组");
                group.setGroupStatus(2);
                i++;
                group.setGrouperNo(teacherList.get(0).getTeacherNo());
                group.setGrouperName(teacherList.get(0).getTeacherName());
                groupService.addGroup((Group) ClassUtil.checkNull(group));
                for (TeacherInfo t:teacherList
                ) {
                    teacherService.updateTeacherGroupName(t.getUserId(),group.getGroupName());
                }
            }
            return ResultUtil.success("完成随机分组！！!");
        }catch (Exception e){
            return  ResultUtil.success("分组数过多，请至少保证两人一组！！！");
        }

    }

    /**
     * 获取分组列表
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/getGroups")
    public Object getGroups(int page,int rows){
        Map<String,Object> map= (Map<String, Object>) groupService.getAllGroup(page,rows);
        List<Group> list= (List<Group>) map.get("rows");
        for (Group g :list) {
            List<TeacherInfo> list1=teacherService.getTeacherByGroupName(g.getGroupName());
            String teachers="";
            String students="";
            for (TeacherInfo t:list1
                 ) {
                teachers+=t.getTeacherNo()+"-"+t.getTeacherName()+" ";
               List<StudentTeacherRelation> studentTeacherRelations=relationService.getStudentAgreeByTeacherNo(t.getTeacherNo());
                for (StudentTeacherRelation s :studentTeacherRelations
                        ) {
                    students+=s.getStudentNo()+"-"+s.getStudentName()+" ";
                }

                }
            map.put(g.getGroupName()+"teacher",teachers);
            map.put(g.getGroupName()+"student",students);
            }
        return map;
        }
}
