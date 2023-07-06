package com.example.testapp.service.users;

import com.example.testapp.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    User addUser(User user);
    User getUser(Long userId);
    void deleteUser(Long userId);
    User updateUser(User updateUser);


}
