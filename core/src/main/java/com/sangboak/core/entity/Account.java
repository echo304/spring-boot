package com.sangboak.core.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "accounts")
@SQLDelete(sql="Update accounts SET deleted = 1 where id=?")
@Where(clause="deleted = 0")
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length=50)
    private String name;

    @Column(nullable = false, unique = true, length=50)
    private String email;

    @Column(nullable = false, length=200)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @Builder
    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
