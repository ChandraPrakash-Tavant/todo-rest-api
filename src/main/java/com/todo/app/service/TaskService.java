package com.todo.app.service;

import com.todo.app.dto.TaskRequestDTO;
import com.todo.app.entity.Tasks;
import com.todo.app.entity.Users;
import com.todo.app.enums.TaskStatus;
import com.todo.app.exception.TaskNotFoundException;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.repository.TaskRepository;
import com.todo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository theTaskRepository, UserRepository theUserRepository){
        taskRepository = theTaskRepository;
        userRepository = theUserRepository;
    }


    //create new tasks
    public Tasks createTask(int userId,TaskRequestDTO taskRequestDTO){
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with Id: " + userId));
        Tasks createdTask = new Tasks();

        createdTask.setTaskName(taskRequestDTO.getTaskName());
        createdTask.setDescription(taskRequestDTO.getDescription());
        createdTask.setStatus(taskRequestDTO.getStatus());
        createdTask.setUser(user);

        return taskRepository.save(createdTask);
    }

    //get all tasks of user by id
    public List<Tasks> getAllTasksOfUser(int userId){
        return taskRepository.findByUser_Id(userId);
    }

    public Tasks updateTasksById(int taskId, TaskRequestDTO updatedTask){
        Tasks currentTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found By Id: " + taskId));
        if(updatedTask.getTaskName() != null){
            currentTask.setTaskName(updatedTask.getTaskName());
        }
        if(updatedTask.getDescription() != null){
            currentTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus() != null){
            currentTask.setStatus(updatedTask.getStatus());
        }
        return taskRepository.save(currentTask);
    }

    public void deleteTaskById(int taskId){
        Tasks currentTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found by id:" + taskId));
        taskRepository.delete(currentTask);
    }

    public List<Tasks> getAllTasksByStatus(TaskStatus status){
        return taskRepository.findAllTasksByStatus(status);
    }

}
