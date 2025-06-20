package com.example.backendproject.stompwensocket.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class GPTService {

    //json문자열 <-> 자바객체, json객체
    private final ObjectMapper mapper = new ObjectMapper();

    public String gptMessage(String message) throws Exception{


        //API 호출을 위한 본문 작성
        Map<String,Object> requestBody  = new HashMap<>();
        requestBody .put("model","gpt-4o");
        requestBody .put("input",message);


        //http 요청 작성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/responses"))
                .header("Authorization","Bearer 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")
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody))) //본문 삽입
                .build();


        //요청 전송 및 응답 수신
        HttpClient  client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode(); // HTTP 상태 코드
        System.out.println("에러코드: "+statusCode);

        //응답을 Json으로 파싱
        JsonNode jsonNode = mapper.readTree(response.body());
        System.out.println("gpt 응답 : "+jsonNode);

//        if(statusCode==429){
//
//        }

        //메세지 부분만 추출하여 반환
        String gptMessageResponse = jsonNode.get("output").get(0).get("content").get(0).get("text").asText();
        return gptMessageResponse;

    }

}
