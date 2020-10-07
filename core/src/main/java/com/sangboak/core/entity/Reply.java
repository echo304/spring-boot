package com.sangboak.core.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "replies")
@SQLDelete(sql = "Update replies SET deleted = 1 where id=?")
@Where(clause = "deleted = 0")
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Account author;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Post post;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public Reply(String content, Post post, Account author) {
        this.content = content;
        this.post = post;
        this.author = author;
    }
}