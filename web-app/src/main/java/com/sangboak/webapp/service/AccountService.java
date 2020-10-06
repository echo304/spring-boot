package com.sangboak.webapp.service;

import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Role;
import com.sangboak.core.repository.AccountRepository;
import com.sangboak.core.repository.RoleRepository;
import com.sangboak.webapp.config.CustomSecurityUser;
import com.sangboak.webapp.dto.AccountJoinRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    final private AccountRepository accountRepository;
    final private RoleRepository roleRepository;

    @Transactional
    public Long joinUser(AccountJoinRequestDto accountDto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        Account account = Account.builder()
                .email(accountDto.getEmail())
                .password(accountDto.getPassword())
                .name(accountDto.getName())
                .build();

        Role role = roleRepository.findByName("MEMBER").orElseThrow();
        account.addRole(role);

        return accountRepository.save(account).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(userEmail).orElseThrow();
        return new CustomSecurityUser(account);
    }
}
