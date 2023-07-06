package com.example.testapp.service.users;

import com.example.testapp.exceptions.MainError;
import com.example.testapp.exceptions.MainException;
import com.example.testapp.model.User;
import com.example.testapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new MainException(MainError.NOT_FOUND_ERROR, "No such user!");

        return userRepository.findById(userId).get();
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) throw new MainException(MainError.NOT_FOUND_ERROR, "No such user!");

        User user = userRepository.getReferenceById(userId);
        userRepository.delete(user);
    }
    @Override
    public User updateUser(User updateUser) {
        if (!userRepository.existsById(updateUser.getId())) throw new MainException(MainError.NOT_FOUND_ERROR, "No such user!");

        return userRepository.save(updateUser);
    }
}
