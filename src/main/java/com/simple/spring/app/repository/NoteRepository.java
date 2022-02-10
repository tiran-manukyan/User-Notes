package com.simple.spring.app.repository;

import com.simple.spring.app.entity.Note;
import com.simple.spring.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUser(User user);

}