package ru.isa.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task {

    private String title;
    private String description;
    private User author;
    private Status status; // "в ожидании", "в процессе", "завершено"
    private Priority priority;




    public enum Priority {
        HIGH, MEDIUM, LOW
    }
    public enum Status {
        IN_PROGRESS, COMPLETED, IN_IDLE
    }
}
