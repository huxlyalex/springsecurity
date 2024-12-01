package com.example.springsecurity.service;

import com.example.springsecurity.entity.UserPrincipal;
import com.example.springsecurity.entity.Users;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService { // this class is to verify the user entered by the user is present in the database or not

    @Autowired
    UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // load user by username is an unimplemented function in UserDetailsService,it has username as parameter
        Users user = new Users();

        try {
            user = userRepo.findByFirstName(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);//here we are returning UserDetail object,UserPrinciple implements UserDetails
        //UserDetails cannot be return as such,since it is an interface it should be implemented

    }
}
