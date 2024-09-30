package com.learning.codelearn.controller;

import com.learning.codelearn.models.Course;
import com.learning.codelearn.models.User;
import com.learning.codelearn.service.CourseService;
import com.learning.codelearn.service.impl.CourseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {
    CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping("/courses")
    public String courses(Model model,  HttpServletRequest request) {
        List<Course> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        HttpSession session = request.getSession();
        User user1 = (User) session.getAttribute("user");
        model.addAttribute("user1", user1);
        return "course-list";
    }
    @GetMapping("/courses/ongoing")
    public String ongoing(Model model,  HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        List<Course> courses = courseService.findAllByUserId(user.getId());
        model.addAttribute("courses", courses);
        return "course-ongoing";
    }
    @GetMapping("courses/{courseId}")
    public String course(@PathVariable long courseId, Model model,HttpServletRequest request) {
        Course course = courseService.findById(courseId).get();
        model.addAttribute("course", course);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "course-detail";
    }

    @GetMapping("/courses/new")
    public String newCourse(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "course-create";
    }
    @PostMapping("courses/new")
    public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult bindingResult, Model model, HttpSession session) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("course", course);
            return "course-create";
        }
        User user = (User)session.getAttribute("user");
        courseService.saveCourse(course,user);
        return "redirect:/courses";
    }

    @GetMapping("courses/{courseId}/edit")
    public String editCourseForm(@PathVariable("courseId") long courseId, Model model) {
        Optional<Course> course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return "course-edit";
    }
    @PostMapping("courses/{courseId}/edit")
    public String updateCourse(@PathVariable("courseId") long courseId, @Valid @ModelAttribute("course") Course course, BindingResult result) {
        if(result.hasErrors()){
        return "course-edit";
        }
        course.setId(courseId);
        courseService.updateCourse(course);
        return "redirect:/courses";
    }
    @GetMapping("courses/{courseId}/delete")
    public String deleteCourse(@PathVariable("courseId") long courseId) {
        courseService.delete(courseId);
        return "redirect:/courses";
    }
    @GetMapping("courses/search")
    public String searchCourses(@RequestParam(value = "query") String query,  Model model) {
        List<Course> courses =     courseService.findByQuery(query);
        model.addAttribute("courses", courses);
        return "course-list";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
}
