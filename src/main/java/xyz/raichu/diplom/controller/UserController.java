package xyz.raichu.diplom.controller;

import org.springframework.web.bind.annotation.*;
import xyz.raichu.diplom.entity.User;
import xyz.raichu.diplom.service.UserService;

import java.util.List;

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

    @GetMapping()
    public List<User> get(){
        return userService.getAll();
    }

    @GetMapping("/me")
    public User getMe(){
        return userService.getCurrentUser();
    }

    @PostMapping("/{id}")
    public User toggle(@PathVariable Long id){
        return userService.toggle(id);
    }
}
