package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.dao.StudentTeacherRelationDao;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname StudentTeacherRelationServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/29 20:20
 */
@Service("studentTeacherRelationService")
public class StudentTeacherRelationServiceImpl implements StudentTeacherRelationService {
    @Autowired
    private StudentTeacherRelationDao studentTeacherRelationDao;

    @Override
    public int addStudentTeacherRelation(StudentTeacherRelation studentTeacherRelation) {
        return studentTeacherRelationDao.addStudentTeacherRelation(studentTeacherRelation);
    }

    @Override
    public List<StudentTeacherRelation> getStudentTeacherRelationByStudentNo(String studentNo) {
        return studentTeacherRelationDao.getStudentTeacherRelationByStudentNo(studentNo);
    }

    @Override
    public Object getStudentSelectThesisByTeacherNo(int page,int rows,String teacherNo) {
        Map<String,Object> map=new HashMap<>();
        Page<StudentTeacherRelation> pageRecord = new Page<>(page,rows);
        List<StudentTeacherRelation> list=studentTeacherRelationDao.getStudentSelectThesisByTeacherNo(pageRecord,teacherNo,3);
        for (StudentTeacherRelation studentTeacherRelation:list){
            if(studentTeacherRelation.getStudentEmail().isEmpty()||studentTeacherRelation.getStudentEmail()==""){
                studentTeacherRelation.setStudentEmail("无");
            }
            if(studentTeacherRelation.getStudentPhone().isEmpty()||studentTeacherRelation.getStudentPhone()==""){
                studentTeacherRelation.setStudentPhone("无");
            }
            if(studentTeacherRelation.getOpinionFlag()==0){
                studentTeacherRelation.setTeacherOpinion("未给出");
                studentTeacherRelation.setOpinionFlagStr("未处理");
            }
            else {
                if(studentTeacherRelation.getTeacherOpinion().isEmpty()||studentTeacherRelation.getTeacherOpinion()==""){
                    studentTeacherRelation.setTeacherOpinion("无");
                }
                if(studentTeacherRelation.getOpinionFlag()==1){
                    studentTeacherRelation.setOpinionFlagStr("已同意");
                }
                if (studentTeacherRelation.getOpinionFlag()==2){
                    studentTeacherRelation.setOpinionFlagStr("已拒绝");
                }
            }
        }
        map.put("total",pageRecord.getTotal());
        map.put("rows",list);
        return map;
    }

    @Override
    public int getStudentSelectThesisAgreeNumByTeacherNo(String teacherNo, int flag) {
        return studentTeacherRelationDao.getStudentSelectThesisAgreeNumByTeacherNo(teacherNo,flag);
    }


    @Override
    public int operateStudent(StudentTeacherRelation studentTeacherRelation) {
        return studentTeacherRelationDao.operateStudent(studentTeacherRelation);
    }

    @Override
    public int deleteRelationByThesisNo(String thesisNo) {
        return studentTeacherRelationDao.deleteRelationByThesisNo(thesisNo);
    }

    @Override
    public StudentTeacherRelation getStudentTeacherRelationByThesisNo(String thesisNo) {
        return studentTeacherRelationDao.getStudentTeacherRelationByThesisNo(thesisNo);
    }

    @Override
    public Object getAgreeThesisByTeacherNo(int page, int rows, String teacherNo) {
      Map<String,Object> map=new HashMap<>();
      Page<StudentTeacherRelation> pageRecord=new Page<>(page,rows);
      List<StudentTeacherRelation> list=studentTeacherRelationDao.getStudentSelectThesisByTeacherNo(pageRecord,teacherNo,1);
      map.put("total",pageRecord.getTotal());
      map.put("rows",list);
      return  map;
    }

    @Override
    public int updateTaskUrlByThesisNo(String taskUrl, String thesisNo) {
        return studentTeacherRelationDao.updateTaskUrlByThesisNo(taskUrl,thesisNo);
    }

    @Override
    public int updateThesisUrlByThesisNo(String thesisUrl, String thesisNo) {
        return studentTeacherRelationDao.updateThesisUrlByThesisNo(thesisUrl,thesisNo);
    }

    @Override
    public Object getAgreeThesisByStudentNo(int page, int rows, String studentNo, int opinionFlag) {
      Map<String,Object> map=new HashMap<>();
      Page<StudentTeacherRelation> page1=new Page<>(page,rows);
      List<StudentTeacherRelation> list=studentTeacherRelationDao.getAgreeThesisByStudentNo(page1,studentNo,opinionFlag);
      map.put("total",page1.getTotal());
      map.put("rows",list);
      return  map;
    }

}
