package com.todo.app.dto;

import com.todo.app.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskRequestDTO {

    @NotBlank(message = "TaskName cannot be empty")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    private String taskName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Status cannot be null")
    private TaskStatus status;

    public TaskRequestDTO(){}

    public TaskRequestDTO(String taskName, String description, TaskStatus status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
