package com.sangboak.adminapp.config;

import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CustomSecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private Account account;

    public CustomSecurityUser(Account account) {
        super(account.getEmail(), account.getPassword(), makeGrantedAuth(account.getRoles()));
        this.account = account;
    }

    private static List<GrantedAuthority> makeGrantedAuth(Set<Role> roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(
                role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()))
        );
        return list;
    }
}
