package com.example.backendproject.stompwensocket.controller;

import com.example.backendproject.stompwensocket.dto.ChatMessage;
import com.example.backendproject.stompwensocket.gpt.GPTService;
import com.example.backendproject.stompwensocket.redis.RedisPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Clock;

@Controller
@RequiredArgsConstructor
public class ChatController {

    //단일 브로드캐스트(방이 동적으로 생성이 안됨)
//    @MessageMapping("/chat.sendMessage") //클라이언트가 메세지를 보내면
//    @SendTo("/topic/public") // 메세지를 받음
//    public ChatMessage sendMessage(ChatMessage message){
//        System.out.println(message.getMessage());
//        return message;

    //06.20 gpt 챗봇
    private final GPTService gptService;

    @MessageMapping("/gpt")
    public void sendMessageGPT(ChatMessage message) throws Exception {

        template.convertAndSend("/topic/gpt", message); //내가 보낸 메세지 출력

        //GPT 메세지 반환
        String getResponse = gptService.gptMessage(message.getMessage());
        ChatMessage chatMessage = new ChatMessage("난 GPT",getResponse);
        template.convertAndSend("/topic/gpt", chatMessage);

        //        try{
//            String getResponse = gptService.gptMessage(message.getMessage());
//            ChatMessage chatMessage = new ChatMessage("난 GPT",getResponse);
//            template.convertAndSend("/topic/gpt", chatMessage);
//        } catch (Exception e) {
//            // 오류 응답 전달
//            ChatMessage errorMsg = new ChatMessage("GPT 시스템", "⚠ 오류 발생: " + e.getMessage());
//            template.convertAndSend("/topic/gpt", errorMsg);
//        }
    }


    //서버가 클라이언트에게 수동으로 메세지를 보낼 수 있도록 하는 클래스
    private final SimpMessagingTemplate template;

    //동적으로 방 생성 가능
    //도커 복습겸 추가
    @Value("${PROJECT_NAME:web Server}")
    private String instansName;

    //06.19 redis
    private RedisPublisher redisPublisher;
    private ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/chat.sendMessage")
    public void sendmessage(ChatMessage message) throws JsonProcessingException {
        //message에서 roomId를 추출해서 해당 roomId를 구독하고 있는 클라이언트에게 메세지를 전달
        System.out.printf("Sending message: %s%n", message);

        message.setMessage(instansName+" "+message.getMessage());

        String channel = null;
        String msg = null;

//        if(message.getTo() != null && !message.getRoomId().isEmpty()){
//            //귓속말
//            //내 아이디로 귓속말경로를 활성화 함
//            template.convertAndSendToUser(message.getTo(),"/queue/private", message);
//        } else {
//            //일반메세지
//            //message에서 roomId를 추출해서 해당 roomId를 구독하고 있는 클라이언트에게 메세지를 전달
//            template.convertAndSend("/topic/"+message.getRoomId(), message);
//        }
        if (message.getTo() != null && !message.getTo().isEmpty()) {
                // 귓속말
                //내 아이디로 귓속말경로를 활성화 함
            channel = "private."+message.getRoomId();
            msg = objectMapper.writeValueAsString(message);
        } else {
            // 일반 메시지
            channel = "room."+message.getRoomId();
            msg = objectMapper.writeValueAsString(message);
        }

        redisPublisher.publish(channel,msg);
    }
}
