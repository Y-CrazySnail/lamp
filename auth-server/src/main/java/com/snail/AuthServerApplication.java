package com.snail;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AuthServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthServerApplication.class).run(args);
    }
}
