package com.example.backendproject.stompwensocket.handler;

import java.security.Principal;

//닉네임을 식별하는 인터페이스 Principal
public class StompPrincipal implements Principal {
    private final String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
