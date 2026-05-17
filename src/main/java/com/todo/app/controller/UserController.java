package com.todo.app.controller;


import com.todo.app.dto.UserRequestDTO;
import com.todo.app.entity.Users;
import com.todo.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService theUserService){
        userService = theUserService;
    }


    @PostMapping
    public ResponseEntity<Users> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable int id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }



}
