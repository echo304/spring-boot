package com.sangboak.core.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "boards")
@SQLDelete(sql="Update boards SET deleted = 1 where id=?")
@Where(clause="deleted = 0")
public class Board {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Setter
    @Column(length = 500, nullable = false)
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Post> posts = new HashSet<Post>();

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Formula("(select count(*) from posts ps where ps.deleted = false and ps.board_id = id)")
    private int totalPostCount;

    @Builder
    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
