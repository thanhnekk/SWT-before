package com.learning.codelearn.service;

import com.learning.codelearn.models.Course;
import com.learning.codelearn.models.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAllCourses();
    void saveCourse(Course course, User user);

    Optional<Course> findById(long courseId);

    void updateCourse(Course course);

    void delete(long courseId);

    List<Course> findByQuery(String query);

    List<Course> findAllByUserId(long userId);

}
