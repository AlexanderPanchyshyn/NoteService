package com.pb.test.service;

import com.pb.test.exception.UserNotFoundException;
import com.pb.test.model.User;
import com.pb.test.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User saveUser(@NotNull User user) {
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());

        userRepository.deleteById(id);
        return userRepository.save(userToUpdate);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
