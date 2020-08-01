package com.wustzdy.springboot.maven.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan("com.example.springboot.demo.springboottest.service")
@SpringBootApplication(scanBasePackages = "com.wustzdy.springboot.maven.test") //1
public class SpringboottestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringboottestApplication.class, args);
    }

}
