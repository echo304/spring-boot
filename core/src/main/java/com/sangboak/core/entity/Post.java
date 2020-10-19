package com.sangboak.core.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "posts")
@SQLDelete(sql = "Update posts SET deleted = true where id=?")
@Where(clause = "deleted = false")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Account author;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Reply> replies = new HashSet<Reply>();

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public Post(String title, String content, Account author, Board board) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.board = board;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
