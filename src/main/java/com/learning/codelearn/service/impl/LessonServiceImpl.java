package com.learning.codelearn.service.impl;

import com.learning.codelearn.models.Lesson;
import com.learning.codelearn.repository.CourseRepository;
import com.learning.codelearn.repository.LessonRepository;
import com.learning.codelearn.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;
    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository) {
            this.lessonRepository = lessonRepository;
            this.courseRepository = courseRepository;
    }
    @Override
    public List<Lesson> findAllLessons(long courseId) {
        return lessonRepository.findAllByCourseId(courseId);
    }

    @Override
    public Lesson findById(long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public Lesson save(Lesson lesson, long courseId) {
        lesson.setCourse(courseRepository.findById(courseId).get());
        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteById(long id) {
        lessonRepository.deleteById(id);
    }
}
