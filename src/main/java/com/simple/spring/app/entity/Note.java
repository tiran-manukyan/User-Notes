package com.simple.spring.app.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String note;

    @Embedded
    ActivityDates activityDates;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ActivityDates getActivityDates() {
        return activityDates;
    }

    public void setActivityDates(ActivityDates activityDates) {
        this.activityDates = activityDates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id.equals(note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}