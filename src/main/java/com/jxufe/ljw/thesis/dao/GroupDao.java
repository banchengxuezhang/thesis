package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jxufe.ljw.thesis.bean.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname GroupDao
 * @Author: LeJunWen
 * @Date: 2020/3/12 23:44
 */
@Mapper
public interface GroupDao {
    int addGroup(Group group);
 List<Group> getAllGroup(Pagination pagination);
 List<Group> getGroupNames();
 Group getGroupByGroupName(String groupName);
 Group getGroupById(String groupId);
 int updateGroupByGroupName(Group group);
 int cleanGroup();
 int deleteGroupById(String groupId);
}
