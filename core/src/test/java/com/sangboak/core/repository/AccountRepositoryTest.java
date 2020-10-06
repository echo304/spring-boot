package com.sangboak.core.repository;

import com.sangboak.core.entity.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findById_SoftDeleted_ReturnNull() {
        Account account = accountRepository.save(Account.builder()
            .name("test")
            .email("test@test.com")
            .password("test")
            .build());

        Assertions.assertThat(accountRepository.findAll()).isNotEmpty();

        Long id = account.getId();
        accountRepository.delete(account);

        Optional<Account> deletedAccount = accountRepository.findById(id);
        Assertions.assertThat(deletedAccount).isEmpty();
    }

    @Test
    public void findAll_SoftDeleted_Excluded() {
        Account account = accountRepository.save(Account.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .build());

        Assertions.assertThat(accountRepository.findAll()).isNotEmpty();

        accountRepository.delete(account);

        List<Account> accountList = accountRepository.findAll();
        Assertions.assertThat(accountList).isEmpty();
    }

    @Test
    public void deleteById_Account_DeleteSoftly() {
        Account account = accountRepository.save(Account.builder()
                .name("test")
                .email("test@test.com")
                .password("test")
                .build());

        Assertions.assertThat(accountRepository.findAll()).isNotEmpty();

        Long id = account.getId();
        accountRepository.deleteById(id);

        List<Account> accountList = accountRepository.findAll();
        Assertions.assertThat(accountList).isEmpty();
    }
}