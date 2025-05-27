package com.lec.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.lec.spring.repository")
@SpringBootApplication
@EnableScheduling
public class WearupApplication {

    public static void main(String[] args) {
        SpringApplication.run(WearupApplication.class, args);
    }

}
