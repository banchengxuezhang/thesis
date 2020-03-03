package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.util.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Classname HelloController
 * @Author: LeJunWen
 * @Date: 2020/2/24 21:41
 */
@RestController
public class UtilController {
    private static final Logger logger = LoggerFactory.getLogger(UtilController.class);
    /**
     * 验证码生成工具
     *
     * @param response
     * @param session
     */

    @GetMapping("/logincode")
    public void testCode(HttpServletResponse response,HttpSession session){
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            String text = VerifyCodeUtils.outputVerifyImage(120,38,os,4);
            session.setAttribute("loginCode", text);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }


    }
}
