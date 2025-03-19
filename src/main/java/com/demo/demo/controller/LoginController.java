package com.demo.demo.controller;

import com.demo.demo.dto.LoginForm;
import com.demo.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        log.info("going login page-------------");
        return "login";
    }

    @GetMapping("/upload")
    public String upload() {
        log.info("going upload page-------------");
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            userService.processExcelFile(file);
            model.addAttribute("message", "Users uploaded successfully from file: " + file.getOriginalFilename());
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload file: " + e.getMessage());
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(/*@RequestParam String username, @RequestParam String password,*/ @ModelAttribute(name = "loginForm") LoginForm loginForm, Model model) {
        log.info("inside login");
        boolean isAuthenticated = userService.authenticate(loginForm.getUsername(), loginForm.getPassword());
        if (isAuthenticated) {
            //model.addAttribute("message", "Login successful!");
            return "home";
        } else {
            model.addAttribute("invalidCreds", true);
            return "login";
        }
    }


}