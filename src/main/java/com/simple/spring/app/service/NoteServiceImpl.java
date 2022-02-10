package com.simple.spring.app.service;

import com.simple.spring.app.entity.ActivityDates;
import com.simple.spring.app.entity.Note;
import com.simple.spring.app.entity.User;
import com.simple.spring.app.exception.NoteNotFoundException;
import com.simple.spring.app.repository.NoteRepository;
import com.simple.spring.app.validation.NoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserService userService;

    @Autowired
    NoteValidator noteValidator;

    @Autowired
    ContextSecurityService contextSecurityService;

    @Override
    public Note createNote(Note note) {
        noteValidator.validate(note);
        note.setUser(userService.getCurrentUser());
        note.setActivityDates(new ActivityDates(LocalDateTime.now()));
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note) {
        noteValidator.validate(note);
        Note dbNote = getNote(note.getId());
        note.setUser(dbNote.getUser());
        ActivityDates activityDates = dbNote.getActivityDates();
        activityDates.setUpdateDate(LocalDateTime.now());
        note.setActivityDates(activityDates);

        contextSecurityService.checkSecurityNote(note);

        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id) {
        Note note = getNote(id);

        contextSecurityService.checkSecurityNote(note);

        noteRepository.delete(note);
    }

    @Override
    public List<Note> getUserNotes(User user) {
        return noteRepository.findAllByUser(user);
    }

    @Override
    public Note getNote(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
    }
}