package com.demo.demo.controller;

import com.demo.demo.LoginForm;
import com.demo.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        System.out.println("inside login-------------");
        return "login";
    }

    @PostMapping("/upload")
    public String uploadExcelFile(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {
            userService.processExcelFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "Users uploaded successfully from file: " + file.getOriginalFilename());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to upload file: " + e.getMessage());
        }

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(/*@RequestParam String username, @RequestParam String password,*/ @ModelAttribute(name = "loginForm") LoginForm loginForm, Model model) {
        System.out.println("inside login");
        boolean isAuthenticated = userService.authenticate(loginForm.getUsername(), loginForm.getPassword());
        if (isAuthenticated) {
            //model.addAttribute("message", "Login successful!");
            return "home";
        } else {
            model.addAttribute("invalid creds", true);
            return "login";
        }
    }


}