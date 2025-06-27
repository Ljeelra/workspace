package com.example.backendproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
//스프링에서 모든 컨트롤러의 예외를 한 곳에서 처리하기 위한 어노테이션
public class GlobalExceptionHandler {

    //컨트롤러에서 RuntimeException 에러가 발생했을 때 이 메서드가 대신 처리하도록 매핑
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                2000,
                "내가 전달하는 메세지",
                e.getMessage()

        );

        log.error(errorResponse.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }

}
