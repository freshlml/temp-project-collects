package com.fresh.temp.yui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fresh.temp.yui", "com.fresh.xy.common.exception"})
public class YuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuiApplication.class, args);
    }
}
