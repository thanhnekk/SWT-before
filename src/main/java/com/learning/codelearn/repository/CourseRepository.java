package com.learning.codelearn.repository;

import com.learning.codelearn.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByName(String name);

    @Query("SELECT c from Course c WHERE c.name LIKE CONCAT('%', :query, '%')")
    List<Course> findByQuery(String query);

    List<Course> findAllByUserId(long userId);
}
