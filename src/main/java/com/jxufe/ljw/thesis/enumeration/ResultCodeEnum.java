package com.jxufe.ljw.thesis.enumeration;

import lombok.Getter;

/**
 * @Classname TaskDao
 * @Author: LeJunWen
 * @Date: 2020/2/24 22:30
 */
@Getter
public enum ResultCodeEnum {
    FAIL(2, "失败"), SUCCESS(1, "成功");
    private Integer code;
    private String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
