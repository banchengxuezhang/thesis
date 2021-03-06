package com.jxufe.ljw.thesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author mi
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class ThesisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThesisApplication.class, args);
    }

}
