package com.sangboak.adminapp.controller;

import com.sangboak.adminapp.dto.AccountListResponseDto;
import com.sangboak.adminapp.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    final private AccountService accountService;

    @GetMapping
    public String listPage(Model model) {
        List<AccountListResponseDto> accounts = accountService.getAll();

        model.addAttribute("accounts", accounts);
        return "account/index";
    }
}
