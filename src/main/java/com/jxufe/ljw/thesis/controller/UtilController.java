package com.jxufe.ljw.thesis.controller;

import com.google.code.kaptcha.Producer;
import com.jxufe.ljw.thesis.bean.Task;
import com.jxufe.ljw.thesis.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Classname HelloController
 * @Author: LeJunWen
 * @Date: 2020/2/24 21:41
 */
@RestController
public class UtilController {
    private static final Logger logger = LoggerFactory.getLogger(UtilController.class);
    @Autowired
    private TestService test;
    @RequestMapping("/test")
    public List<Task> index() {
        return test.getAllTasks();
    }
    @Autowired
    private Producer kaptchaProducer;

    @RequestMapping(path = "/logincode", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("loginCode", text);

        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }
}
