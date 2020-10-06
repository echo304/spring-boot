package com.sangboak.adminapp.service;

import com.sangboak.adminapp.config.CustomSecurityUser;
import com.sangboak.core.entity.Account;
import com.sangboak.core.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    final private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(userEmail).orElseThrow();
        return new CustomSecurityUser(account);
    }
}