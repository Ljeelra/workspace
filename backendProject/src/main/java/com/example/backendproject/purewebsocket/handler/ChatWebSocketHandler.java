package com.example.backendproject.purewebsocket.handler;

import com.example.backendproject.purewebsocket.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 동시성 문제를 해결 - 서버에 여러 클라이언트 접속 시에 발생할 수 있는 데이터 손실 고려
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    // json 문자열 -> 자바 객체로 변환할때 쓰기 위한것
    private final ObjectMapper objectMapper = new ObjectMapper();
    //방과 방 안에 있는 세션을 관리하는 객체
    //동시에 접근하는 객체가 많기때문에 동시성 문제를 해결하기 위해 ConcurrentHashMap<> 사용
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    //클라이언트가 웹소켓 서버에 접속했을 때 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        //클라이언트가 웹소켓 서버에 접속했을 때 세션 등록
        sessions.add(session);
        System.out.println("접속된 클라이언트 세션 ID " + session.getId());
    }

    //클라이언트가 보낸 메세지를 서버가 받았을 때 호출
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        //json 문자열 -> 자바 객체 변환
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        String roomID = chatMessage.getRoomId();//클라이언트에게 받은 메세지에서 roomID를 추출

        if(!rooms.containsKey(roomID)){//방을 관리하는 객체에 현재 세션이 들어가는 방이 있는지 확인
            rooms.put(roomID, ConcurrentHashMap.newKeySet()); //없으면 새로운 방을 생성
        }
        //해당 방에 세션 추가
        rooms.get(roomID).add(session);

        //for(WebSocketSession s : sessions) {
        for(WebSocketSession s : rooms.get(roomID)) {
            if(s.isOpen()) {
                //자바 객체 -> json 문자열
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
                System.out.println("전송된 메세지" + chatMessage.getMessage());
            }
        }

    }

    //클라이언트의 연결이 끊어졌을 때 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    super.afterConnectionClosed(session, status);

    //종료된 메세지 세션 삭제
    sessions.remove(session);

    }
}
