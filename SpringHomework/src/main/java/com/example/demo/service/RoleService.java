package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }
}
