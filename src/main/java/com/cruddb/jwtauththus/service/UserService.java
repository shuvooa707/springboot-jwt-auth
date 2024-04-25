package com.cruddb.jwtauththus.service;

import com.cruddb.jwtauththus.dto.CreateUserDTO;
import com.cruddb.jwtauththus.model.User;
import com.cruddb.jwtauththus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(CreateUserDTO createUserDTO) {
        User newUser = new User();
        newUser.setName(createUserDTO.getName());
        newUser.setUsername(createUserDTO.getUsername());
        newUser.setPassword( passwordEncoder.encode(createUserDTO.getPassword()) );
        newUser.setEmail(createUserDTO.getEmail());
        newUser.setRole("USER");

        userRepository.save(newUser);
    }

    public Page<User> allUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    public Optional<User> showUser(Long id) {

        return userRepository.findById(id);
    }
}
