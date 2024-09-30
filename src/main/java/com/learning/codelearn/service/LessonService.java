package com.learning.codelearn.service;

import com.learning.codelearn.models.Lesson;
import com.learning.codelearn.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface LessonService {
    List<Lesson> findAllLessons(long courseId);
    Lesson findById(long id);
    Lesson save(Lesson lesson,long courseId);
    void deleteById(long id);
}
