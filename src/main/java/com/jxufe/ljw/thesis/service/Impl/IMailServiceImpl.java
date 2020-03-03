package com.jxufe.ljw.thesis.service.Impl;

import com.jxufe.ljw.thesis.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @Classname IMailServiceImpl
 * @Author: LeJunWen
 * @Date: 2020/3/1 19:22
 */

@Service("imailService")
public class IMailServiceImpl implements IMailService {
    static {
        System.setProperty("mail.mime.splitlongparameters", "false");
        System.setProperty("mail.mime.charset", "UTF-8");
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

    /**
     * html邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(subject);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }

    /**
     * 带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String fileName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath, fileName));
            if ((fileName != null) && ((fileName.toLowerCase().indexOf("gb2312") != -1) || (fileName.toLowerCase().indexOf("gbk") != -1))) {
                fileName = MimeUtility.decodeText(fileName);
                fileName = fileName.replace("\\r", "").replace("\\n", "");
            }
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。附件名为：" + fileName);
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("发送邮件时发生异常！", e);
        }


    }
}


