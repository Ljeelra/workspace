package com.example.backendproject.Auth.entity;

import com.example.backendproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tokenType;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Auth(User user, String refreshToken, String accessToken, String tokenType ) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    // updateAccessToken 메서드 추가
    //토큰값을 업데이트 해주는 메서드
    public void updateAccessToken(String newAccessToken) {
        this.accessToken = newAccessToken;
    }

    // updateRefreshToken 메서드 추가
    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

}
