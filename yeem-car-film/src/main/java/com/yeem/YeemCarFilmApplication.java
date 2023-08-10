package com.yeem;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YeemCarFilmApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(YeemCarFilmApplication.class).run(args);
    }
}
