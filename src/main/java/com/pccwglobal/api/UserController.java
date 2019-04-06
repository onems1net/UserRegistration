package com.pccwglobal.api;

import com.google.common.collect.Iterables;
import com.pccwglobal.exception.UserNotFoundException;
import com.pccwglobal.model.User;
import com.pccwglobal.service.EmailService;
import com.pccwglobal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public ModelAndView showHomePage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Iterable<User> users = userService.findAllUsers();

        if (Iterables.size(users)>0)
            model.addAttribute("users", users);

        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView showSignUpForm(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-user");
        return modelAndView;
    }

    @PostMapping("/adduser")
    public ModelAndView addUser(@Valid User user, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-user");
        } else {
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            modelAndView.setViewName("index");
            emailService.sendEmail(
                    user.getEmail(),
                    "No-Reply: Registration confirmed",
                    "Hello " + user.getFirstName() + " " + user.getLastName() + ", you have successfully registered!");
        }

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        model.addAttribute("user", user);
        modelAndView.setViewName("update-user");
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            user.setId(id);
            model.addAttribute("user", user);
            modelAndView.setViewName("update-user");
        } else {
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
        userService.deleteUser(user);

        Iterable<User> users = userService.findAllUsers();

        if (Iterables.size(users)>0)
            model.addAttribute("users", users);

        modelAndView.setViewName("index");
        return modelAndView;
    }
}
