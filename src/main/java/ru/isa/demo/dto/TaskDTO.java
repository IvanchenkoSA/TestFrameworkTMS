package ru.isa.demo.dto;

import ru.isa.demo.model.Task;

public record TaskDTO(Task.Priority priority, Task.Status status, String description, String title, Long userId) {
}
