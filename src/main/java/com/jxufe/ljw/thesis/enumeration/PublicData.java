package com.jxufe.ljw.thesis.enumeration;

import lombok.Getter;

/**
 * @Classname PublicData
 * @Author: LeJunWen
 * @Date: 2020/3/2 00:13
 */
@Getter
public enum PublicData {
    PATH("D:\\毕业设计管理本地数据盘");
    private String path;

    PublicData(String path){
        this.path = path;
    }
}
