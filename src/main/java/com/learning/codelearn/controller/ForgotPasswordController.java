package com.learning.codelearn.controller;

import com.learning.codelearn.models.User;
import com.learning.codelearn.service.UserService;
import com.learning.codelearn.service.impl.UserNotFoundException;
import com.learning.codelearn.service.impl.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    UserService userService;
    @Autowired
    JavaMailSender mailSender;
    public ForgotPasswordController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle","Forgot Password");
        return "forgot_password_form";
    }
    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String token = buffer.toString();

        try {
            userService.updateResetPasswordToken(token,email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(resetPasswordLink);

            sendEmail(email, resetPasswordLink);

            model.addAttribute("message","We have sent a reset password link to your email!");
        } catch (UserNotFoundException e) {
            model.addAttribute("error",e.getMessage());
           // throw new RuntimeException(e);

        } catch (UnsupportedEncodingException  | MessagingException e) {

            model.addAttribute("error","Error while sending email.");
        }

        return "forgot_password_form";
    }

    public void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("anhbhnhe180164@fpt.edu.vn","Kode Web");
        mimeMessageHelper.setTo(email);

        String subject = "Here's the link to reset password";

        String content = "<p>Hello,</p>"+"<p>You have requested to reset your password. </p>"
                +"<p>Click the link below to change your password: </p>"

                +"<a href=\""+resetPasswordLink+"\">Change my password</a>";
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        mailSender.send(mimeMessage);
    }
    @GetMapping("/reset_password")
    public String showResetPasswordForm(Model model, @Param(value="token") String token) throws UserNotFoundException {
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("message","Invalid Token");
            model.addAttribute("title","Reset your password");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("title","Reset your password");
        return "reset_password_form";
    }
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) throws UserNotFoundException {
    String token = request.getParameter("token");
    String password = request.getParameter("password");
    User user = userService.get(token);
    if (user == null) {
        model.addAttribute("message","Invalid Token");
        model.addAttribute("title","Reset your password");
        return "message";
    }else{
        userService.updatePassword(user,password);
        return "redirect:/courses?update";
    }
     }

}
