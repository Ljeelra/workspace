package com.example.backendproject.stompwensocket.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes){
        String nickname = getNickname(request.getURI().getQuery());
        return new StompPrincipal(nickname);
    }
    //닉네임이 있으면 사용자를 식별하는 클래스 StompPrincipal에서 사용자가 누구인지 추출하는 핸들러
    private String getNickname(String query){
        if(query == null || !query.contains("nickname=")){
            return "닉네임없음";
        }
        else{
            return query.split("nickname=")[1];
        }
    }
}
