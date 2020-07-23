package com.example.springboot.demo.springboottest.controller;

import com.example.springboot.demo.springboottest.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@RestController
public class TestController {
    @RequestMapping("/test")
    public String tets() {
        return "Hello World";
    }

    @Autowired
    private I18nService i18nService;


    @GetMapping("/hello-coder")
    public ResponseEntity greeting() {
        return ResponseEntity.ok(i18nService.getMessage("message.key.hello", new Object[]{"JavaCoder"}));
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok(i18nService.getMessage("message.key.test"));
    }


}
