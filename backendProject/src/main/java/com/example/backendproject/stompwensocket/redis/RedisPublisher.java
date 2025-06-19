package com.example.backendproject.stompwensocket.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor//생성자 자동생성
@Component
public class RedisPublisher {

    /**메세지를 발행하는 클래스**/
    private final StringRedisTemplate stringRedisTemplate;

    // stomp -> pub ->sub ->stomp
    public void publish(String channel, String message){
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
