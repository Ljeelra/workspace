package com.example.backendproject.Auth.repository;

import com.example.backendproject.Auth.entity.Auth;
import com.example.backendproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Long> {

    //
    boolean existsByUser(User user);

    //RefreshToken이 있는지 확인하는 쿼리
    Optional<Auth> findByRefreshToken(String refreshToken);

    Optional<Auth> findByUser(User user);
}
