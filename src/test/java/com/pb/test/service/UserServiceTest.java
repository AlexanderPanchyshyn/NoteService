package com.pb.test.service;

import com.pb.test.exception.UserNotFoundException;
import com.pb.test.model.entity.User;
import com.pb.test.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void whenSavingUserShouldSave(){
        User user = new User();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.saveUser(user);
        Assertions.assertEquals(savedUser,user);
        verify(userRepository).save(user);
    }

    @Test
    public void shouldFindUserWithGivenValidId(){
        User user = new User();
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User user2 = userService.getUser(user.getId());
        Assertions.assertEquals(user,user2);
        verify(userRepository).findUserByUsername(user.getUsername());
    }

    @Test
    public void shouldThrowExceptionForUserWithGivenInvalidId(){
        User user = new User();
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenThrow(new UserNotFoundException());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(user.getId()));
        verify(userRepository).findUserByUsername(user.getUsername());
    }

    @Test
    public void shouldFindListWithTwoUsers(){
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(list);
        List<User> allUsers = userService.getAll();
        Assertions.assertEquals(2,allUsers.size());
    }

    @Test
    public void shouldFindEmptyListOfUsers(){
        List<User> list = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(list);
        List<User> allUsers = userService.getAll();
        Assertions.assertEquals(0,allUsers.size());
    }

    @Test
    public void withValidIdShouldDeleteUser(){
        User user = new User();
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    public void shouldDeleteAllUsers(){
        userService.deleteAll();
        verify(userRepository).deleteAll();
    }

    @Test
    public void shouldUpdateUserWithGivenId(){
        User user = new User();
        User user2 = new User();
        user2.setPassword("password");
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user2);
        User updatedUser = userService.updateUser(user.getId(), user);
        Assertions.assertEquals("password",updatedUser.getPassword());
    }
}