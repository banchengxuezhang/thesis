package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname TaskDao
 * @Author: LeJunWen
 * @Date: 2020/2/24 22:37
 */
@Mapper
public interface TaskDao {
     List<Task> getAllTasks();
}
