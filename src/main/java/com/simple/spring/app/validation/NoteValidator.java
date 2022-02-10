package com.simple.spring.app.validation;

import com.simple.spring.app.entity.Note;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component()
public class NoteValidator {

    private static final int TITLE_MAX_LENGTH = 50;
    private static final int NOTE_MAX_LENGTH = 1000;

    public void validate(Note note) throws RuntimeException {
        if (!StringUtils.hasText(note.getTitle())) {
            throw new RuntimeException("Title cannot be null or empty.");
        }
        if (note.getTitle().length() > TITLE_MAX_LENGTH) {
            throw new RuntimeException(String.format("Title length must be less than or equal to %d.", TITLE_MAX_LENGTH));
        }
        if (note.getNote() != null && note.getNote().length() > NOTE_MAX_LENGTH) {
            throw new RuntimeException(String.format("Note length must be less than or equal to %d.", NOTE_MAX_LENGTH));
        }
    }

}
