package com.example.springsecurity.controller;

import com.example.springsecurity.entity.Users;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/saveuser")
    public Users saveUser(@RequestBody Users user)
    {
        System.out.println("password");
        System.out.println(user.getPassword());

        return  userService.registerUser(user);//registering new users

    }
    @PostMapping("/loginuser")
    public String verify(@RequestBody Users users)
    {
        return userService.verify(users);
    }
}
