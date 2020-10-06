package com.sangboak.webapp.controller;

import com.sangboak.webapp.dto.AccountJoinRequestDto;
import com.sangboak.webapp.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
@Log
@AllArgsConstructor
public class AccountController {
    final private AccountService accountService;

    @GetMapping("/signup")
    public String signupPage(@ModelAttribute("accountDto") AccountJoinRequestDto accountDto, Model model) {
        return "/account/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid AccountJoinRequestDto dto,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            model.addAttribute("accountDto", dto);

            for (FieldError error: errors.getFieldErrors()) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }

            return "/account/signup";
        }

        accountService.joinUser(dto);

        return "redirect:/account/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/account/login";
    }

    @GetMapping("/login/result")
    public String loginResultPage() {
        return "/account/login-success";
    }

    @GetMapping("/logout/result")
    public String logoutResultPage() {
        return "/account/logout";
    }
}
