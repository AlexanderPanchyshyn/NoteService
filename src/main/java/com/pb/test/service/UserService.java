package com.pb.test.service;

import com.pb.test.exception.UserAlreadyExistsException;
import com.pb.test.exception.UserNotFoundException;
import com.pb.test.model.entity.Role;
import com.pb.test.model.entity.User;
import com.pb.test.model.security.SecurityUser;
import com.pb.test.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public User saveUser(@NotNull User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        user.setRoles(Arrays.asList(new Role("USER", Arrays.asList("READ", "WRITE"))));

        return userRepository.save(user);
    }

    public User updateUser(String username, User user) {
        User userToUpdate = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        userToUpdate.setPassword(user.getPassword());

        userRepository.deleteById(user.getId());
        return userRepository.save(userToUpdate);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        return new SecurityUser(user);
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
