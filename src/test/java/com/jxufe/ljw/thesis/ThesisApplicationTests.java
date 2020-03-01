package com.jxufe.ljw.thesis;
import com.jxufe.ljw.thesis.service.IMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThesisApplicationTests {
    @Autowired
    private IMailService iMailService;

    @Test
    void contextLoads() {
        iMailService.sendSimpleMail("1213159526@qq.com","测试","ceh");

    }

}
