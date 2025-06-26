package com.example.backendproject.security.core;

import com.example.backendproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    //UserDetails <- 사용자 정보를 담는 인터페이스
    //로그인한 사용자의 정보를 담아두는 역할
    //=>유저정보 조회해서 객체 만든것

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //User의 권한을 반환하는 메서드
        //Collection.sinleton <- 이 사용자는 한가지 권한만 갖는다는 의미
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    //토큰에서 추출한 사용자 정보의 id를 반환 (테이블의 pk값)
    //User 엔티티에서 ID추출
    public long getId(){
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); //User 엔티티에서 password 반환
    }

    @Override
    public String getUsername() {
        return user.getUserid(); //USer 엔티티에서 userid 반환, Unique한 값(이 사람을 식별할 수 있는 값)
    }
    
    /** 아래는 현재 계정 상태를 판단하는 메서드 **/
    //return 값은 임시로 true로 설정, 나중에 entity에서 별도 컬럼을 만들어서 boolean값 반환하도록 해야함
    @Override   //현재 계정 상태
    public boolean isEnabled() {
        return true;
    }

    @Override //이 계정이 만료되었는지
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override //이 계정이 잠겨있는지
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override //자격증명이 만료되지 않았는지
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
