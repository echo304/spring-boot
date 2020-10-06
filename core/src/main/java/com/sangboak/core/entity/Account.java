package com.sangboak.core.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "accounts")
@SQLDelete(sql="Update accounts SET deleted = true where id=?")
@Where(clause="deleted = false")
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

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<AccountRoleAssignment> roles = new HashSet<>();

    @Builder
    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void addRole(Role role) {
        AccountRoleAssignment accountRoleAssignment = AccountRoleAssignment.builder()
                .account(this)
                .role(role)
                .build();
        roles.add(accountRoleAssignment);
    }

    public void removeRole(Role role) {
        for (Iterator<AccountRoleAssignment> iterator = roles.iterator();
             iterator.hasNext(); ) {
            AccountRoleAssignment accountRoleAssignment = iterator.next();

            if (accountRoleAssignment.getAccount().equals(this) &&
                    accountRoleAssignment.getRole().equals(role)) {
                iterator.remove();

                accountRoleAssignment.setAccount(null);
                accountRoleAssignment.setRole(null);
            }
        }
    }
}
