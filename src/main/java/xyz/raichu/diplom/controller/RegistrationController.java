package xyz.raichu.diplom.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.service.UserService;

/**
 * 25.05.2021
 * RegistrationController
 * 01:54
 */

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User register(@RequestBody User registration){
        return userService.register(registration);
    }
}
