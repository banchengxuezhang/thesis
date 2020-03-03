package com.jxufe.ljw.thesis.dao;

import com.jxufe.ljw.thesis.bean.Init;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname InitDao
 * @Author: LeJunWen
 * @Date: 2020/2/29 10:46
 */
@Mapper
public interface InitDao {
    Init getInitInfo();

    int updateInitInfo(Init init);
}
