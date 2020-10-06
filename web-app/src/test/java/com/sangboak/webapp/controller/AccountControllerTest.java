package com.sangboak.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangboak.core.entity.Account;
import com.sangboak.core.entity.Role;
import com.sangboak.core.repository.AccountRepository;
import com.sangboak.core.repository.RoleRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signup_WithProperData_CreateAccount() throws Exception {
        roleRepository.save(new Role("MEMBER"));

        mockMvc.perform(MockMvcRequestBuilders.post("/account/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "test@test.com")
                .param("name", "test")
                .param("password", "test")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/account/login"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());

        Account account = accountRepository.findByEmail("test@test.com").orElseThrow();
        assertThat(account.getEmail()).isEqualTo("test@test.com");
        assertThat(account.getName()).isEqualTo("test");
    }
}