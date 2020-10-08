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
@Entity(name = "accounts")
@SQLDelete(sql="Update accounts SET deleted = 1 where id=?")
@Where(clause="deleted = 0")
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(nullable = false, length=50)
    private String name;

    @Column(nullable = false, unique = true, length=50)
    private String email;

    @Setter
    @Column(nullable = false, length=200)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "account_role_assignments",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    @Formula("(select count(*) from posts ps where ps.deleted = false and ps.author_id = id)")
    private int totalPostCount;

    @Formula("(select count(*) from replies r where r.deleted = false and r.author_id = id)")
    private int totalReplyCount;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id")
    private Ranking ranking;

    @Builder
    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
    public void removeRole(Role role) { this.roles.remove(role); }

    public boolean isAdmin() {
        return this.roles.stream().anyMatch(role -> role.getName().equals("ADMIN"));
    }
}
