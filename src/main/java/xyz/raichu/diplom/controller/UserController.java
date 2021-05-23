package xyz.raichu.diplom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.service.UserService;

/**
 * 24.05.2021
 * UserController
 * 04:56
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getMe(){
        return userService.getCurrentUser();
    }
}
