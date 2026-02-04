package com.fresh.temp.yui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.temp.yui", "com.fresh.common.exception"})
public class YuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuiApplication.class, args);
    }
}
