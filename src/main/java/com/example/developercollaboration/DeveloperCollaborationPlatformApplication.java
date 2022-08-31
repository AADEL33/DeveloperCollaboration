package com.example.developercollaboration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.developercollaboration.*"})
public class DeveloperCollaborationPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeveloperCollaborationPlatformApplication.class, args);
    }



}