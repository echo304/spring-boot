package com.sangboak.adminapp.controller;

import com.sangboak.adminapp.dto.AccountCreateRequestDto;
import com.sangboak.adminapp.dto.AccountListResponseDto;
import com.sangboak.adminapp.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/create")
    public String createPage(@ModelAttribute("accountDto") AccountCreateRequestDto dto) {
        return "account/create";
    }

    @PostMapping("/create")
    public String create(
            Model model,
            @Valid AccountCreateRequestDto dto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("accountDto", dto);

            for (FieldError error: errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }

            return "account/create";
        }

        accountService.createAccount(dto);

        return "redirect:/accounts";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);

        return "redirect:/accounts";
    }
}
