package com.example.life_community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.life_community.mapper")
public class LifeCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeCommunityApplication.class, args);
    }

}
