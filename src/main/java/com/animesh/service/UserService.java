package com.animesh.service;

import com.animesh.model.User;
import java.util.Optional;

public interface UserService {
    public User saveUser(User user);
    public Optional<User> findUserByEmail(String email);
    public Iterable<User> findAllUsers();
    public Optional<User> findUserById(long id);
    public void deleteUser(User user);
}
