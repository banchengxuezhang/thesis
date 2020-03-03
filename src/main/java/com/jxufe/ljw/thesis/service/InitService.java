package com.jxufe.ljw.thesis.service;

import com.jxufe.ljw.thesis.bean.Init;

/**
 * @Classname InitService
 * @Author: LeJunWen
 * @Date: 2020/2/29 11:11
 */
public interface InitService {
    Init getInitInfo();

    int updateInitInfo(Init init);
}
