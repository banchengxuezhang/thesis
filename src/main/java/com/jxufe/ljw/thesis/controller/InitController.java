package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.Init;
import com.jxufe.ljw.thesis.service.InitService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname InitController
 * @Author: LeJunWen
 * @Date: 2020/2/29 10:45
 */
@RequestMapping("/init")
@RestController
public class InitController {
    private static final Logger logger= LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private InitService initService;
    @GetMapping("/getInitInfo")
    public Object getInitInfo(){
        logger.info("-------------------开始获取数据--------------------");
       try {

           return ResultUtil.success(initService.getInitInfo());
       }catch (Exception e){
           return  ResultUtil.error("获取数据出错！！！");
       }
    }
    @PostMapping("/updateInitInfo")
    public Object updateInitInfo(Init init){
        try{
            Init data=initService.getInitInfo();
            init.setInitId(data.getInitId());
            initService.updateInitInfo(init);
        }catch (Exception e){
            return  ResultUtil.success("更新数据出错！！！");
        }
        return  ResultUtil.success(init);
    }
}