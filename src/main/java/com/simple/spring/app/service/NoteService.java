package com.simple.spring.app.service;

import com.simple.spring.app.entity.Note;
import com.simple.spring.app.entity.User;

import java.util.List;

public interface NoteService {

    Note createNote(Note note);

    Note updateNote(Note note);

    void deleteNote(Long id);

    List<Note> getUserNotes(User user);

    Note getNote(Long id);
}