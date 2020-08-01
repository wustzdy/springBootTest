package com.wustzdy.springboot.maven.test.controller;

import com.wustzdy.springboot.maven.test.service.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
@Slf4j
@RestController
public class TestController {
    @RequestMapping("/test1")
    public String test(@RequestParam String id) {
        log.info("业务日志。。。。。/test....." + id);
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
