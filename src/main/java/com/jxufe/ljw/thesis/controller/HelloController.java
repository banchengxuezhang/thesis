package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.Task;
import com.jxufe.ljw.thesis.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname HelloController
 * @Author: LeJunWen
 * @Date: 2020/2/24 21:41
 */
@RestController
public class HelloController {
    @Autowired
    private TestService test;
    @RequestMapping("/test")
    public List<Task> index() {
        return test.getAllTasks();
    }
}
