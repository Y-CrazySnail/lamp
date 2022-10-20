package com.snail.pet;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableOAuth2Sso
@SpringBootApplication
@EnableScheduling
public class Pet {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Pet.class).run(args);
    }
}
