package com.learning.codelearn.controller;

import com.learning.codelearn.dto.RegistrationDto;
import com.learning.codelearn.models.User;
import com.learning.codelearn.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/loginn")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "loginn";
    }
    @GetMapping("/lessons/code")
    public String codePage(Model model) {
        model.addAttribute("user", new User());
        return "code";
    }
    @PostMapping("/loginn")
    public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
        // Example of authentication logic (you should implement your own)
        User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());

        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("name", user.getUsername());
            return "redirect:/courses?loginn";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/loginn?error";
        }
    }

    @GetMapping("/logoutt")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/loginn?logout";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult bindingResult, Model model) {
            User existingUserEmail = userService.findByEmail(user.getEmail());
            if(existingUserEmail != null && existingUserEmail.getEmail()!=null && !existingUserEmail.getEmail().isEmpty()){
                return "redirect:/register?fail";
            }
        User existingUsername = userService.findByUsername(user.getUsername());
        if(existingUsername != null && existingUsername.getUsername()!=null && !existingUsername.getUsername().isEmpty()){
            return "redirect:/register?fail";
        }
            if(bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                return "register";
            }
        userService.save(user);
        return "redirect:/courses?success";
    }

}
