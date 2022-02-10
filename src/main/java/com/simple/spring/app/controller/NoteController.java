package com.simple.spring.app.controller;

import com.simple.spring.app.dto.NoteDto;
import com.simple.spring.app.dto.converter.NoteConverter;
import com.simple.spring.app.entity.Note;
import com.simple.spring.app.entity.User;
import com.simple.spring.app.service.NoteServiceImpl;
import com.simple.spring.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notes")
public class NoteController {

    @Autowired
    NoteServiceImpl noteService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping()
    public ResponseEntity<List<NoteDto>> getUserNotes() {
        User currentUser = userService.getCurrentUser();
        List<Note> userNotes = noteService.getUserNotes(currentUser);
        return ResponseEntity.ok(NoteConverter.convertToNoteDtoList(userNotes));
    }

    @PostMapping()
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {
        Note note = NoteConverter.convertToNote(noteDto);
        Note savedNote = noteService.createNote(note);
        return ResponseEntity.ok(NoteConverter.convertToNoteDto(savedNote));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable("id") Long id, @RequestBody NoteDto noteDto) {
        Note note = NoteConverter.convertToNote(noteDto);
        note.setId(id);
        Note updatedNote = noteService.updateNote(note);
        return ResponseEntity.ok(NoteConverter.convertToNoteDto(updatedNote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNote(@PathVariable("id") Long id) {
        noteService.deleteNote(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }
}