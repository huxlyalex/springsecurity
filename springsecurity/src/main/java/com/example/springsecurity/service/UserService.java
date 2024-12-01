package com.example.springsecurity.service;

import com.example.springsecurity.entity.Users;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService { //this class is register new user
    // and to generate token for corresponding user

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword())); // here we use Bcrypt password enconder to encrupt the incoming password and save it to db
        //bcrpt generate a hash value of the password ,and save it to db,the hash value cannot be converted into original password
        return userRepository.save(user);

    }

    public String verify(Users users) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getFirstName(), users.getPassword()));
        if (authentication.isAuthenticated())// it is used to find,if the login user is present or not
        {
            return jwtService.generateToken(users.getFirstName()); //if present generate token for the corresponding user

        }
        return "no user found";

    }
}
