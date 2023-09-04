package com.popug.tasktracker.userservice.controller;

import com.popug.tasktracker.userservice.entity.PopugUser;
import com.popug.tasktracker.userservice.repository.PopugUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "restful/users")
@Secured("ADMIN")
public class PopugUserRestController {

    @Autowired
    private PopugUserRepository popugUserRepository;

    @PostMapping
    public PopugUser createUser(@RequestBody PopugUser user) {
        user.setId(null);
        return popugUserRepository.save(user);
    }

    @GetMapping("{id}")
    public PopugUser getUser(@PathVariable Long id) {
        return popugUserRepository.findById(id).orElseThrow();
    }

    @PutMapping("{id}")
    public PopugUser updateUser(@PathVariable Long id, @RequestBody PopugUser user) {
        user.setId(id);
        return popugUserRepository.save(user);
    }

    @DeleteMapping("{id}")
    public void updateUser(@PathVariable Long id) {
        PopugUser user = popugUserRepository.findById(id).orElseThrow();
        popugUserRepository.delete(user);
    }


    @GetMapping
    public Iterable<PopugUser> getUsers() {
        return popugUserRepository.findAll();
    }
}