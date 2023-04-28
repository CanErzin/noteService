package com.canerzin.notes.service.controller;

import com.canerzin.notes.service.dto.UserDto;
import com.canerzin.notes.service.model.User;
import com.canerzin.notes.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) throws Exception {
        User user = userService.createUser(userDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> findUser(@PathVariable("username") String username) throws Exception {
        return new ResponseEntity<>(userService.getUserByUserName(username), HttpStatus.OK);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username,@RequestBody UserDto userDto) throws Exception {
        return new ResponseEntity<User>(userService.updateUser(username,userDto), HttpStatus.OK);
    }


}
