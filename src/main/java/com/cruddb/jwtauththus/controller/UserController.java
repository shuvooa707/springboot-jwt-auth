package com.cruddb.jwtauththus.controller;


import com.cruddb.jwtauththus.dto.CreateUserDTO;
import com.cruddb.jwtauththus.model.User;
import com.cruddb.jwtauththus.repository.UserRepository;
import com.cruddb.jwtauththus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> index(@RequestParam(name = "size", defaultValue = "0") Integer size, @RequestParam(name = "page", defaultValue = "10") Integer page) {
        Page<User> userPage = userService.allUsers(size, page);
        return ResponseEntity.ok(userPage);
    }

    public ResponseEntity<?> show(@PathVariable(name = "id") Long id) {
        Optional<User> userOptional = userService.showUser(id);
        return ResponseEntity.ok(userOptional.get());
    }

    @PostMapping({"create", "/create"})
    public ResponseEntity<?> create(@RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.createUser(createUserDTO);
        } catch (Exception exception) {
            return ResponseEntity.ok(new Object(){
                public final String message = "failed";
                public final String error = exception.getMessage();
            });
        }

        return ResponseEntity.ok(new Object(){
            public final String message = "success";
        });
    }
}
