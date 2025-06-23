package com.example.backendproject.stompwensocket.config;

import com.example.backendproject.stompwensocket.handler.CustomHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration//이 클래스는 스프링의 설정 클래스라고 등록하는 어노테이션
@EnableWebSocketMessageBroker// Stomp 메시지 브로커 기능을 활성화하는 어노테이션
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Prefix
        /**구독용 Prefix**/
        // /topic 일반 채팅을 받을 접두어
        // /queue 귓속말을 받을 접두어
        /** 서버가 보내는 메세지를 클라이언트가 구독할 때 사용하는 경로**/
        registry.enableSimpleBroker("/topic","/queue"); // 구독용 경로 서버 -> 클라이언트


        /**전송용 Prefix**/
        // 클라이언트가 서버에게 보낼 메세지 접두어
        registry.setApplicationDestinationPrefixes("/app"); //전송용 경로 클라이언트 -> 서버

        // /usere 귓속말을 받을 접두어
        /** 서버가 특정 사용자에게 메세지를 보낼 떄, 클라이언트가 구독할 경로 접두어**/
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //단체 채팅을 위한 엔드포인트
        registry.addEndpoint("/ws-chat")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .setAllowedOriginPatterns("*");

        //gpt를위한 엔드포인트
        registry.addEndpoint("/ws-gpt")
                .setAllowedOriginPatterns("*");
    }

}
