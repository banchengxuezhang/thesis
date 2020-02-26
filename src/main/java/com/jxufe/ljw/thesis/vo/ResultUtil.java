package com.jxufe.ljw.thesis.vo;

import com.google.common.collect.Lists;
import com.jxufe.ljw.thesis.enumeration.ResultCodeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname RestResult
 * @Author: LeJunWen
 * @Date: 2020/2/25 21:35
 */
@Data
public class ResultUtil {
    public static ResultVo success(Object data){
        return new ResultVo(ResultCodeEnum.SUCCESS.getCode(),data,ResultCodeEnum.SUCCESS.getMsg());
    }

    public static ResultVo success(String msg){
        return new ResultVo(ResultCodeEnum.SUCCESS.getCode(),null,msg);
    }

    public static ResultVo error(String msg){
        return new ResultVo(ResultCodeEnum.FAIL.getCode(),null,msg);
    }

    public static Map<String, Object> dataGridEmptyResult() {
        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("rows", Lists.newArrayList());
        return result;
    }
}
