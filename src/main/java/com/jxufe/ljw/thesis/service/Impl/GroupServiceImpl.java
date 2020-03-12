package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jxufe.ljw.thesis.bean.Group;
import com.jxufe.ljw.thesis.dao.GroupDao;
import com.jxufe.ljw.thesis.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname GroupServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/3/13 00:18
 */
@Service("groupService")
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupDao groupDao;

    @Override
    public int addGroup(Group group) {
        return groupDao.addGroup(group);
    }

    @Override
    public Object getAllGroup(int page, int rows) {
        Map<String,Object> map=new HashMap<>();
        Page<Group> page1=new Page<>(page,rows);
        List<Group> list=groupDao.getAllGroup(page1);
        map.put("total",page1.getTotal());
         map.put("rows",list);
         return map;
    }

    @Override
    public Group getGroupByGroupName(String groupName) {
        return groupDao.getGroupByGroupName(groupName);
    }
}
