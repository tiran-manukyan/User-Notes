package com.simple.spring.app.dto.converter;

import com.simple.spring.app.dto.NoteDto;
import com.simple.spring.app.entity.Note;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NoteConverter {

    public static NoteDto convertToNoteDto(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setTitle(note.getTitle());
        noteDto.setNote(note.getNote());
        noteDto.setUserDto(UserConverter.convertToUserDto(note.getUser()));

        return noteDto;
    }

    public static Note convertToNote(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getId());
        note.setTitle(noteDto.getTitle());
        note.setNote(noteDto.getNote());

        return note;
    }

    public static List<NoteDto> convertToNoteDtoList(List<Note> notes) {
        if (CollectionUtils.isEmpty(notes)) return Collections.emptyList();
        return notes.stream().map(NoteConverter::convertToNoteDto).collect(Collectors.toList());
    }
}
