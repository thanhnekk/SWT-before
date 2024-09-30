package com.learning.codelearn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Icon should not be empty")
    private String iconUrl;
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @ManyToMany(mappedBy = "courses")
    private List<User> users=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="created_by")
    private User user;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();



}
