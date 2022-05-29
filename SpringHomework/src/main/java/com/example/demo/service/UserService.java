package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Users saveUser(Users user){
        String encoded =  new BCryptPasswordEncoder().encode(user.getCevaaltceva());
        user.setCevaaltceva(encoded);
        return userRepository.save(user);
    }
}
