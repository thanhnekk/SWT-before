package com.learning.codelearn.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    @CreationTimestamp
    private LocalDate joinDate;
    private LocalDate dob;
    private String resetPasswordToken;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List <Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="registration",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="course_id",referencedColumnName = "id")}
    )
    private List <Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Course> createdCourses = new ArrayList<>();

    public Course findEnrolled(Course course){
        for(Course c: this.getCourses()){
            if(c.getId().equals(course.getId())){
                return c;
            }
        }
        return null;
    }

    public void addCourse(Course course) {
        this.getCourses().add(course);
    }
}
