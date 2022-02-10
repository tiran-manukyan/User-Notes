package com.simple.spring.app.service;

import com.simple.spring.app.entity.ActivityDates;
import com.simple.spring.app.entity.User;
import com.simple.spring.app.exception.UserAlreadyExistsException;
import com.simple.spring.app.exception.UserNotFoundException;
import com.simple.spring.app.repository.UserRepository;
import com.simple.spring.app.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserValidator userValidator;

    @Override
    public void createUser(User user) {
        userValidator.validate(user);
        String email = user.getEmail();
        user.setActivityDates(new ActivityDates(LocalDateTime.now()));

        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(email);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}