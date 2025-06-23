package com.example.backendproject.user.repository;

import com.example.backendproject.Auth.entity.Auth;
import com.example.backendproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByUserid(String userid);
}
