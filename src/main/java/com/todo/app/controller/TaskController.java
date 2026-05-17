package com.todo.app.controller;


import com.todo.app.dto.TaskRequestDTO;
import com.todo.app.entity.Tasks;
import com.todo.app.enums.TaskStatus;
import com.todo.app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService theTaskService){
        taskService = theTaskService;
    }

    @PostMapping("/users/{userId}/tasks")
    public ResponseEntity<Tasks> createTask(@PathVariable int userId, @Valid @RequestBody TaskRequestDTO taskRequestDTO){
        return new ResponseEntity<>(taskService.createTask(userId,taskRequestDTO),HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<Tasks>> getAllTasksOfUser(@PathVariable int userId){
        return new ResponseEntity<>(taskService.getAllTasksOfUser(userId),HttpStatus.OK);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Tasks> updateTasksById(@PathVariable int taskId, @RequestBody TaskRequestDTO updatedTask){
        return new ResponseEntity<>(taskService.updateTasksById(taskId, updatedTask), HttpStatus.OK) ;
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String>  deleteTaskById(@PathVariable int taskId){
        taskService.deleteTaskById(taskId);
        return new ResponseEntity<>("Task deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Tasks>> getAllTasksByStatus(@RequestParam TaskStatus status){
        return new ResponseEntity<>(taskService.getAllTasksByStatus(status),HttpStatus.OK);
    }

}
