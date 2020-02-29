package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.bean.Init;
import com.jxufe.ljw.thesis.dao.InitDao;
import com.jxufe.ljw.thesis.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname InitServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/2/29 11:12
 */
@Service("initService")
public class InitServiceImpl implements InitService {
    @Autowired
    private InitDao initDao;
    @Override
    public Init getInitInfo() {
        return initDao.getInitInfo();
    }

    @Override
    public int updateInitInfo(Init init) {
        return initDao.updateInitInfo(init);
    }
}
