package com.sangboak.adminapp.service;

import com.sangboak.adminapp.config.CustomSecurityUser;
import com.sangboak.adminapp.dto.AccountDto;
import com.sangboak.adminapp.dto.AccountListResponseDto;
import com.sangboak.adminapp.dto.AccountSaveRequestDto;
import com.sangboak.adminapp.dto.AccountUpdateRequestDto;
import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Role;
import com.sangboak.core.repository.AccountRepository;
import com.sangboak.core.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    final private AccountRepository accountRepository;
    final private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(userEmail).orElseThrow();
        return new CustomSecurityUser(account);
    }

    @Transactional
    public void saveAccount(AccountSaveRequestDto dto) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        Account account = Account.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .build();

        if (dto.isAdmin()) {
            Role role = roleRepository.findByName("ADMIN").orElseThrow();
            account.addRole(role);
        }

        Role role = roleRepository.findByName("MEMBER").orElseThrow();
        account.addRole(role);

        accountRepository.save(account);
    }

    @Transactional
    public void saveAccount(Long id, AccountUpdateRequestDto dto) {
        Account account = accountRepository.findById(id).orElseThrow();

        account.setName(dto.getName());
        if (dto.isResetPassword()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            account.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        accountRepository.save(account);
    }

    @Transactional
    public List<AccountListResponseDto> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(AccountListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public AccountDto findById(Long id) throws Exception {
        Account account = accountRepository.findById(id).orElseThrow();
        return AccountDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .admin(account.isAdmin())
                .deleted(account.isDeleted())
                .build();
    }
}