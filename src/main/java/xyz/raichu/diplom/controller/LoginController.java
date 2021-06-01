package xyz.raichu.diplom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 02.06.2021
 * LoginController
 * 03:58
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String get(Model model) {
        return "login";
    }

}
