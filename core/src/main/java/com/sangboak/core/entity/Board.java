package com.sangboak.core.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "boards")
@SQLDelete(sql="Update boards SET deleted = 1 where id=?")
@Where(clause="deleted = 0")
public class Board {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
