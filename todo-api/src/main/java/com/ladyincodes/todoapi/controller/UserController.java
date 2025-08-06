package com.ladyincodes.todoapi.controller;

import com.ladyincodes.todoapi.payload.response.UserProfileResponse;
import com.ladyincodes.todoapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public UserProfileResponse getCurrentUserProfile() {
        return userService.getCurrentUserProfile();
    }
}
