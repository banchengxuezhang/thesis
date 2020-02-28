package com.jxufe.ljw.thesis.enumeration;

import lombok.Getter;
/**
 * @Classname TaskDao
 * @Author: LeJunWen
 * @Date: 2020/2/24 22:33
 */
@Getter
public enum UserType {
    TEACHER(2),STUDENT(3),MANAGE(1);
    private int type;

    UserType(int type){
        this.type = type;
    }
}
