package com.learning.codelearn.controller;

import com.learning.codelearn.models.User;
import com.learning.codelearn.repository.CourseRepository;
import com.learning.codelearn.repository.LessonRepository;
import com.learning.codelearn.repository.UserRepository;
import com.learning.codelearn.service.CourseService;
import com.learning.codelearn.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EnrollController {
    UserService userService;
    public EnrollController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/courses/enroll/{courseId}")
    public String enroll(@PathVariable long courseId,  HttpSession session) {
    User user = (User)session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            return "redirect:/loginn?enroll"; // Redirect to login page if user is not logged in
        }
    userService.saveCourse(user,courseId);
    return "redirect:/courses/"+courseId+"?saved";
    }
}
