package com.animesh.api;

import com.animesh.exception.UserAlreadyExistException;
import com.animesh.exception.UserNotFoundException;
import com.animesh.model.User;
import com.animesh.service.EmailService;
import com.animesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/list")
    Iterable<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        String email = user.getEmail();

        userService.findUserByEmail(email).ifPresent(existingUser -> {
            throw new UserAlreadyExistException(email);
        });

        User newUser = userService.saveUser(user);

        emailService.sendEmail(
                user.getEmail(),
                "No-Reply: Registration confirmed",
                "Hello " + user.getFirstName() + " " + user.getLastName() + ", you have successfully registered!");

        return newUser;
    }

    @GetMapping("/find/{id}")
    public User findUser(@PathVariable("id") long id) {
        return userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable("id") long id) {
        return userService.findUserById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    return userService.saveUser(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userService.saveUser(newUser);
                });
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable("id") long id) {
        User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        userService.deleteUser(user);
    }
}
