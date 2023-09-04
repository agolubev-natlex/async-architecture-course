package com.popug.tasktracker.userservice.controller;

import com.popug.tasktracker.userservice.entity.PopugUser;
import com.popug.tasktracker.userservice.repository.PopugUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "users")

public class PopugUserController {
    @Autowired
    private PopugUserRepository popugUserRepository;

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", popugUserRepository.findAll());
        return "users";
    }

    @PostMapping("/add-user")
    public String addUser(PopugUser user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        popugUserRepository.save(user);
        return "redirect:/users";
    }
}