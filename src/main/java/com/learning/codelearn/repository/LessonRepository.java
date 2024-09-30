package com.learning.codelearn.repository;


import com.learning.codelearn.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAll();

    List<Lesson> findAllByCourseId(long courseId);
}
