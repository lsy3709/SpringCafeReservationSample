package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
        // returns users.html
    }


    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create-user";
        // returns create-user.html
    }

    //프로필 이미지 업로드 형식으로, 몽고디비에 연결하는 코드
    @PostMapping
    public String createUser(@ModelAttribute User user, @RequestParam("profileImage") MultipartFile file) {
    log.info("User created" + user, "multipart : " + file
    );
        try {
            if (!file.isEmpty()) {
                User savedUser = userService.createUser(user);
                userService.saveProfileImage(savedUser.getId(), file);
            }
            userService.createUser(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user profile image", e);
        }
        return "redirect:/users";
        // Redirect to the list of users
    }

    @GetMapping("/{id}/edit")
    public String showUpdateUserForm(@PathVariable Long id, Model model) {
        userService.getUserById(id).ifPresent(user -> model.addAttribute("user", user));
        return "user/update-user";
        // returns update-user.html
    }

    @PostMapping("/edit")
    public String updateUser( @ModelAttribute User userDetails) {
        log.info("userDetails id : " + userDetails.getId());
        userService.updateUser( userDetails.getId(), userDetails);
        return "redirect:/users";
        // Redirect to the list of users
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
        // Redirect to the list of users
    }
}
