package com.pccwglobal.service;

import com.pccwglobal.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public User saveUser(User user);
    public User findUserByEmail(String email);
    public Iterable<User> findAllUsers();
    public Optional<User> findUserById(long id);
    public void deleteUser(User user);
}
