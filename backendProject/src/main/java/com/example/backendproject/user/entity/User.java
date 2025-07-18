package com.example.backendproject.user.entity;

import com.example.backendproject.Auth.entity.Auth;
import com.example.backendproject.security.core.Role;
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
public class User extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String userid;

    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)//이 필드를 DB에 문자열로 저장하라는 의미
    private Role role;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Auth auth;

}
