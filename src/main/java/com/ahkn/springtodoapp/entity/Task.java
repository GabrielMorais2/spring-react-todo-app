package com.ahkn.springtodoapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class Task implements Comparable<Task> {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private boolean completed;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task that = (Task) o;

        if (completed != that.completed) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(description, that.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (completed ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(Task task) {
        return this.getId().compareTo(task.getId());
    }

    @Override
    public String toString() {
        return id + ": " + description + ":completed=" + completed;
    }
}
