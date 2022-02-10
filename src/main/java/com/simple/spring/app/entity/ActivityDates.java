package com.simple.spring.app.entity;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class ActivityDates {

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    public ActivityDates() {
    }

    public ActivityDates(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
