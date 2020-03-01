package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jxufe.ljw.thesis.bean.ThesisInfo;
import com.jxufe.ljw.thesis.dao.ThesisInfoDao;
import com.jxufe.ljw.thesis.service.ThesisInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ThesisInfoServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/29 15:43
 */
@Service("thesisInfoService")
public class ThesisInfoServiceImpl implements ThesisInfoService {
    @Autowired
    private ThesisInfoDao thesisInfoDao;
    @Override
    public int addThesisInfo(ThesisInfo thesisInfo) {
        return thesisInfoDao.addThesis(thesisInfo);
    }

    @Override
    public int deleteThesisInfo(String thesisId) {
        return thesisInfoDao.deleteThesis(thesisId);
    }

    @Override
    public Map<String, Object> getThesisInfo(int page, int rows,ThesisInfo thesisInfo) {
       Map<String,Object> map=new HashMap<>();
       Page<ThesisInfo> pageRecord=new Page<>(page,rows);
       StringBuilder stringBuilder = new StringBuilder("");
       if(thesisInfo.getThesisTitle()!=""&&thesisInfo.getThesisTitle()!=null){
           stringBuilder.append('1');
           thesisInfo.setThesisTitle("%"+thesisInfo.getThesisTitle()+"%");
       }else {
           stringBuilder.append('0');
       }
        if(thesisInfo.getTeacherNo()!=""&&thesisInfo.getTeacherNo()!=null){
            stringBuilder.append('1');
            thesisInfo.setTeacherNo(thesisInfo.getTeacherNo()+"%");
        }else {
            stringBuilder.append('0');
        }
        if(thesisInfo.getTeacherName()!=""&&thesisInfo.getTeacherName()!=null){
            stringBuilder.append('1');
            thesisInfo.setTeacherName(thesisInfo.getTeacherName()+"%");
        }else {
            stringBuilder.append('0');
        }
       List<ThesisInfo> list=thesisInfoDao.getThesisInfo(pageRecord,String.valueOf(stringBuilder),thesisInfo);
       map.put("total",pageRecord.getTotal());
       map.put("rows",list);
       return map;
    }

    @Override
    public Map<String, Object> getThesisInfoByTeacherNo(int page, int rows, String teacherNo) {
        Map<String,Object> map=new HashMap<>();
        Page<ThesisInfo> pageRecord=new Page<>(page,rows);
        List<ThesisInfo> list= thesisInfoDao.getThesisInfoByTeacherNo(pageRecord,teacherNo);
        map.put("total",pageRecord.getTotal());
        map.put("rows",list);
        return map;
    }

    @Override
    public int updateThesis(ThesisInfo thesisInfo) {
        return thesisInfoDao.updateThesis(thesisInfo);
    }

    @Override
    public ThesisInfo getThesisByThesisId(String thesisId) {
        return thesisInfoDao.getThesisByThesisId(thesisId);
    }

}
