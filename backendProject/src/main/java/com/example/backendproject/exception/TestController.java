package com.example.backendproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class TestController {

//    @GetMapping
//    public ResponseEntity<ErrorMessage> test() {
//        throw new RuntimeException("테스트 중입니다.");
//    }

}

