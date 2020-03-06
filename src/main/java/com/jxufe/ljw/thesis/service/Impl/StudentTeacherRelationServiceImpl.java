package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.jxufe.ljw.thesis.bean.StudentTeacherRelation;
import com.jxufe.ljw.thesis.dao.NoPassThesisDao;
import com.jxufe.ljw.thesis.dao.StudentTeacherRelationDao;
import com.jxufe.ljw.thesis.service.StudentTeacherRelationService;
import com.jxufe.ljw.thesis.util.ClassUtil;
import org.apache.commons.lang3.StringUtils;
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
    private NoPassThesisDao noPassThesisDao;
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
    public Object getStudentSelectThesisByTeacherNo(int page, int rows, String teacherNo,int opinionFlag) {
        Map<String, Object> map = new HashMap<>();
        Page<StudentTeacherRelation> pageRecord = new Page<>(page, rows);
        List<StudentTeacherRelation> list = studentTeacherRelationDao.getStudentSelectThesisByTeacherNo(pageRecord, teacherNo, opinionFlag);
        for (StudentTeacherRelation studentTeacherRelation : list) {
            if (studentTeacherRelation.getStudentEmail().isEmpty() || studentTeacherRelation.getStudentEmail() == "") {
                studentTeacherRelation.setStudentEmail("无");
            }
            if (studentTeacherRelation.getStudentPhone().isEmpty() || studentTeacherRelation.getStudentPhone() == "") {
                studentTeacherRelation.setStudentPhone("无");
            }
            if (studentTeacherRelation.getOpinionFlag() == 0) {
                studentTeacherRelation.setTeacherOpinion("未给出");
                studentTeacherRelation.setOpinionFlagStr("未处理");
            } else {
                if (StringUtils.isEmpty(studentTeacherRelation.getTeacherOpinion())|| studentTeacherRelation.getTeacherOpinion() == "") {
                    studentTeacherRelation.setTeacherOpinion("无");
                }
                if (studentTeacherRelation.getOpinionFlag() == 1) {
                    studentTeacherRelation.setOpinionFlagStr("已同意");
                }
                if (studentTeacherRelation.getOpinionFlag() == 2) {
                    studentTeacherRelation.setOpinionFlagStr("已拒绝");
                }
            }
        }
        map.put("total", pageRecord.getTotal());
        map.put("rows", list);
        return map;
    }

    @Override
    public int getStudentSelectThesisAgreeNumByTeacherNo(String teacherNo, int flag) {
        return studentTeacherRelationDao.getStudentSelectThesisAgreeNumByTeacherNo(teacherNo, flag);
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
        Map<String, Object> map = new HashMap<>();
        Page<StudentTeacherRelation> pageRecord = new Page<>(page, rows);
        List<StudentTeacherRelation> list = studentTeacherRelationDao.getStudentSelectThesisByTeacherNo(pageRecord, teacherNo, 1);
        map.put("total", pageRecord.getTotal());
        map.put("rows", list);
        return map;
    }

    @Override
    public int updateTaskUrlByThesisNo(String taskUrl, String thesisNo) {
        return studentTeacherRelationDao.updateTaskUrlByThesisNo(taskUrl, thesisNo);
    }

    @Override
    public int updateThesisUrlByThesisNo(String thesisUrl, String thesisNo) {
        return studentTeacherRelationDao.updateThesisUrlByThesisNo(thesisUrl, thesisNo);
    }

    @Override
    public Object getAgreeThesisByStudentNo(int page, int rows, String studentNo, int opinionFlag) {
        Map<String, Object> map = new HashMap<>();
        Page<StudentTeacherRelation> page1 = new Page<>(page, rows);
        List<StudentTeacherRelation> list = studentTeacherRelationDao.getAgreeThesisByStudentNo(page1, studentNo, opinionFlag);
        map.put("total", page1.getTotal());
        map.put("rows", list);
        return map;
    }

    @Override
    public Object getAllStudentTeacherDetail(int page, int rows, StudentTeacherRelation studentTeacherRelation) {
        // opinionFlagStr 1 未审核 2 未选题 3 审核通过 4 审核未通过
        Map<String, Object> map = new HashMap();
        List<StudentTeacherRelation> thesisInfoList = studentTeacherRelationDao.getAllStudentTeacherDetail(studentTeacherRelation);
        List<StudentTeacherRelation> noPassThesisList = noPassThesisDao.getSelectAll(studentTeacherRelation);
        for (int i = 0; i < thesisInfoList.size(); i++) {
            thesisInfoList.set(i, (StudentTeacherRelation) ClassUtil.checkNull(thesisInfoList.get(i)));
        }
        for (StudentTeacherRelation noPassThesis : noPassThesisList) {
            noPassThesis.setOpinionFlag(2);
            thesisInfoList.add(noPassThesis);
        }

        // 根据opinionFlagStr过滤数据
        List<StudentTeacherRelation> filterList = Lists.newArrayList();
        String flag = studentTeacherRelation.getOpinionFlagStr();
        if (!StringUtils.isEmpty(flag)) {
            if ("1".equals(flag)) {
                for (int i = 0; i < thesisInfoList.size(); i++) {
                    if (!StringUtils.isEmpty(thesisInfoList.get(i).getThesisNo()) && 0 == thesisInfoList.get(i).getOpinionFlag()) {
                        filterList.add(thesisInfoList.get(i));
                    } else {
                        continue;
                    }
                }
            } else if ("2".equals(flag)) {
                for (int i = 0; i < thesisInfoList.size(); i++) {
                    if (StringUtils.isEmpty(thesisInfoList.get(i).getThesisNo()) && null == thesisInfoList.get(i).getOpinionFlag()) {
                        filterList.add(thesisInfoList.get(i));
                    } else {
                        continue;
                    }
                }
                //thesisInfoList.stream().filter(item -> (StringUtils.isEmpty(item.getThesisNo()) && null == item.getOpinionFlag())).collect(Collectors.toList());
            } else if ("3".equals(flag)) {
                for (int i = 0; i < thesisInfoList.size(); i++) {
                    if ((!StringUtils.isEmpty(thesisInfoList.get(i).getThesisNo()) && thesisInfoList.get(i).getOpinionFlag() != null && 1 == thesisInfoList.get(i).getOpinionFlag())) {
                        filterList.add(thesisInfoList.get(i));
                    } else {
                        continue;
                    }
                }
            } else {
                for (int i = 0; i < thesisInfoList.size(); i++) {
                    if ((!StringUtils.isEmpty(thesisInfoList.get(i).getThesisNo()) && thesisInfoList.get(i).getOpinionFlag() != null && 2 == thesisInfoList.get(i).getOpinionFlag())) {
                        filterList.add(thesisInfoList.get(i));
                    } else {
                        continue;
                    }
                }
            }
        } else {
            filterList = thesisInfoList;
        }
        List<StudentTeacherRelation> subList = Lists.newArrayList();
        // list假分页
        return ClassUtil.getObject(page, rows, map, filterList, subList);
    }


    @Override
    public int getAllDealNum(int opinionFlag) {
        return studentTeacherRelationDao.getAllDealNum(opinionFlag);
    }

    @Override
    public int deleteRelationByAccount(String userAccount) {
        return studentTeacherRelationDao.deleteRelationByAccount(userAccount);
    }


}
