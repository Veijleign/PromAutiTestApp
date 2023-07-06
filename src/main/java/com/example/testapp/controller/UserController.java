package com.example.testapp.controller;

import com.example.testapp.http.user.AddUserRequest;
import com.example.testapp.http.user.UpdateUserRequest;
import com.example.testapp.model.User;
import com.example.testapp.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<User> getUser(
            @RequestParam Long id
    ) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/add-user")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<User> addUser(
            @RequestBody AddUserRequest addUserRequest
    ) {
        User user = User.builder()
                .name(addUserRequest.getName())
                .build();

        return ResponseEntity.ok(userService.addUser(user));
    }

    @PostMapping("/update-user")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<User> updateUser(
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        User user = userService.getUser(updateUserRequest.getUserId());
        user.setName(updateUserRequest.getName());

        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PostMapping("/delete-user")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<String> deleteUser( // question
            @RequestParam Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
