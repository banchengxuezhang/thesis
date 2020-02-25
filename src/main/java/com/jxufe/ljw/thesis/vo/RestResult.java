package com.jxufe.ljw.thesis.vo;

import lombok.Data;

/**
 * @Classname RestResult
 * @Author: LeJunWen
 * @Date: 2020/2/25 21:35
 */
@Data
public class RestResult<T> {

    private T resultData;
    private boolean success = true;
    private String message = "";
    private String code;

    public RestResult() {
        super();
    }

    public RestResult(T resultData) {
        this.resultData = resultData;
    }

    public RestResult(T resultData, boolean success, String message, String code) {
        this.resultData = resultData;
        this.success = success;
        this.message = message;
        this.code = code;
    }
}
