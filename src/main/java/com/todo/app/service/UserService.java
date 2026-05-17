package com.todo.app.service;

import com.todo.app.dto.UserRequestDTO;
import com.todo.app.entity.Users;
import com.todo.app.exception.UserNotFoundException;
import com.todo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRequest;

    @Autowired
    public UserService(UserRepository theUserRepository){
        userRequest = theUserRepository;
    }

    //creating a new user
    public Users createUser(UserRequestDTO userRequestDTO){
        Users createdUser = new Users();

        createdUser.setFirstName(userRequestDTO.getFirstName());
        createdUser.setLastName(userRequestDTO.getLastName());
        createdUser.setEmail(userRequestDTO.getEmail());

        return userRequest.save(createdUser);
    }

    //get a user by his/her id
    public Users getUserById(int id){
        return userRequest.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

}
