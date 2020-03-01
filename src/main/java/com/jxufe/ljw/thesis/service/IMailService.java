package com.jxufe.ljw.thesis.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @Classname IMailService
 * @Author: LeJunWen
 * @Date: 2020/3/1 19:21
 */
@Async
public interface IMailService {

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String to, String subject, String content);



    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
