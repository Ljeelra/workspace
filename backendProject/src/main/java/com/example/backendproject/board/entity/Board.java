package com.example.backendproject.board.entity;

import com.example.backendproject.comment.entity.Comment;
import com.example.backendproject.user.entity.BaseTime;
import com.example.backendproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String batchkey;

    @Column(name = "view_count",nullable = false)
    private Long viewCount = 0L; //기본값 0으로 초기화

    //아래는 글을 작성한 유저 정보Add commentMore actions
    // 연관관계 맵핑
    //다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //일대다
    @OneToMany(mappedBy = "board")
    private List<Comment> comment = new ArrayList<>();

}
