package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.Task;
import com.jxufe.ljw.thesis.dao.TaskDao;
import com.jxufe.ljw.thesis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname testServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/24 23:02
 */
@Service("testService")
public class TestServiceImpl implements TestService {
    @Autowired
    private TaskDao taskDao ;

    @Override
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }
}
