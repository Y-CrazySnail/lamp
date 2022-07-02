package com.snail;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableOAuth2Sso
@SpringBootApplication
@EnableScheduling
public class Game {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Game.class).run(args);
    }
}
