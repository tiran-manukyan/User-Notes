package com.simple.spring.app.service;

import com.simple.spring.app.entity.Note;
import com.simple.spring.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ContextSecurityServiceImpl implements ContextSecurityService {

    @Autowired
    UserService userService;

    @Override
    public void checkSecurityNote(Note note) {
        User currentUser = userService.getCurrentUser();
        User user = note.getUser();
        if (!Objects.equals(currentUser, user)) {
            throw new RuntimeException("You do not have permission to perform this operation.");
        }
    }
}