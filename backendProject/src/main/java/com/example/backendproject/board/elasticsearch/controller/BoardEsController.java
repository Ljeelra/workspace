package com.example.backendproject.board.elasticsearch.controller;

import com.example.backendproject.board.elasticsearch.dto.BoardEsDocument;
import com.example.backendproject.board.elasticsearch.service.BoardEsService;
import com.example.backendproject.board.searchlog.dto.SearchLogMessage;
import com.example.backendproject.board.service.BoardService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
@Builder//builder()가 빨간줄 떠서 붙인 것
public class BoardEsController {

    private final BoardEsService boardEsService;
    
    //스프링에서 카프카로 메세지를 전송하기 위한 컴포넌트
    private final KafkaTemplate<String, SearchLogMessage> kafkaTemplate;

    @GetMapping("/elasticsearch")
    public ResponseEntity<Page<BoardEsDocument>> elasticsearch(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        //검색어 정보 카프카 전송
        String userId = "1";
        String searchedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        SearchLogMessage message = new SearchLogMessage(keyword,userId,searchedAt);
        kafkaTemplate.send("search-log", message); //search-log 토픽으로 메세지 전달


        return ResponseEntity.ok(boardEsService.search(keyword, page,size));
    }

    @GetMapping("/top-keywords")
    public ResponseEntity<List<String>> getTopKeyword(){
        List<String> keywords = boardEsService.getTopSearchKeyword();
        System.out.println(keywords);
        return ResponseEntity.ok(keywords);
    }





}
