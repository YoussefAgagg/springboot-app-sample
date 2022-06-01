package com.github.youssefagagg.springbootappsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootAppSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAppSampleApplication.class, args);
    }

}
