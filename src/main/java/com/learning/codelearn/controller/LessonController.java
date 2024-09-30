package com.learning.codelearn.controller;

import com.learning.codelearn.models.Course;
import com.learning.codelearn.models.Lesson;
import com.learning.codelearn.models.User;
import com.learning.codelearn.repository.CourseRepository;
import com.learning.codelearn.service.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LessonController {
    private final CourseRepository courseRepository;
    LessonService lessonService;
    public LessonController(LessonService lessonService, CourseRepository courseRepository) {
        this.lessonService = lessonService;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/lessons/{lessonId}")
    public String showLesson(@PathVariable("lessonId") long lessonId, Model model) {
        Lesson lesson = lessonService.findById(lessonId);
        model.addAttribute("lesson", lesson);
        Course course = lesson.getCourse();
        model.addAttribute("course", course);
        String courseName = lesson.getCourse().getName();
        model.addAttribute("courseName", courseName);
        return courseName+"_"+lessonId;
    }

    @GetMapping("/lessons/{courseId}/new")
    public String newLesson(Model model, @PathVariable("courseId") long courseId) {
        Lesson lesson = new Lesson();
        model.addAttribute("courseId", courseId);
        model.addAttribute("lesson", lesson);
        return "lesson-create";
    }
    @PostMapping("/lessons/{courseId}/new")
    public String createLesson(@ModelAttribute("lesson") Lesson lesson, @PathVariable("courseId") long courseId, Model model, HttpServletRequest request) {
        lessonService.save(lesson,courseId);
        model.addAttribute("course",courseRepository.findById(courseId).get());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "course-detail";
    }
    @GetMapping("courses/lessons/{lessonId}/del")
    public String deleteLesson(@PathVariable("lessonId") long lessonId, Model model, HttpServletRequest request) {
        Lesson lesson = lessonService.findById(lessonId);
        model.addAttribute("lesson", lesson);
        lessonService.deleteById(lessonId);
        model.addAttribute("course",lesson.getCourse());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "course-detail";
    }
}
