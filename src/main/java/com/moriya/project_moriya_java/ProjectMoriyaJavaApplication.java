package com.moriya.project_moriya_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProjectMoriyaJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMoriyaJavaApplication.class, args);

        System.out.println(new BCryptPasswordEncoder(8).encode("1"));

    }


}
