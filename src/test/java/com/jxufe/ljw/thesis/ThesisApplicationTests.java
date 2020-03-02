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
        String file="D:\\毕业设计管理本地数据盘\\004\\Thesis";
        String file1="毕业论文管理系统设计与实现文献综述.doc";
        iMailService.sendAttachmentsMail("1213159526@qq.com","测试","ceh",file,file1);

    }

}
