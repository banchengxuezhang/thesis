package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.Group;
import javafx.scene.control.Pagination;

import java.util.List;

/**
 * @Classname GroupService
 * @Author: LeJunWen
 * @Date: 2020/3/13 00:17
 */
public interface GroupService {
    int addGroup(Group group);
    Object getAllGroup(int page,int rows);
    Group getGroupByGroupName(String groupName);
    int updateGroupByGroupName(Group group);
    List<Group> getGroupNames();
    int cleanGroup();
    int deleteGroupById(String groupId);
    Group getGroupById(String groupId);
}
