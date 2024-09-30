package com.learning.codelearn.service.impl;

import com.learning.codelearn.dto.RegistrationDto;
import com.learning.codelearn.models.Course;
import com.learning.codelearn.models.Role;
import com.learning.codelearn.repository.CourseRepository;
import com.learning.codelearn.repository.RoleRepository;
import com.learning.codelearn.service.UserService;
import com.learning.codelearn.models.User;
import com.learning.codelearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private  CourseRepository courseRepository;
    private  UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
    }

//    private RegistrationDto mapToUserDTO(User user) {
//        RegistrationDto registrationDto = RegistrationDto .builder()
//                .id(user.getId())
//                .userName(user.getUserName())
//                .joinDate(user.getJoinDate())
//                .build();
//        return registrationDto;
//    }


    @Override
    public void save(RegistrationDto registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());
        user.setEmail(registrationDto.getEmail());
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    @Override
    public void saveCourse(User user, long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        user.addCourse(course.get());
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user != null) {System.out.println(user.getUsername());
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }else throw new UserNotFoundException("Could not found user with email: "+email);
    }

    @Override
    public User get(String resetPasswordToken) {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


}
