package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.Task;
import com.jxufe.ljw.thesis.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname testService
 * @Author: LeJunWen
 * @Date: 2020/2/24 22:53
 */
public interface TestService {

    public List<Task> getAllTasks();
}
