package com.wanjuanshu;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 */
@SpringBootApplication
@EnableSwagger2Doc
public class WJSApplication {

    public static void main(String[] args) {
        SpringApplication.run(WJSApplication.class, args);
    }

}
