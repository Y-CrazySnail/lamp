package com.yeem.lamp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AladdinLampApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AladdinLampApplication.class).run(args);
    }
}
