package com.ladyincodes.todoapi.service;

import com.ladyincodes.todoapi.exception.UserNotFoundException;
import com.ladyincodes.todoapi.model.User;
import com.ladyincodes.todoapi.payload.response.UserProfileResponse;
import com.ladyincodes.todoapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getCurrentUserProfile() {
        String currentUserEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserProfileResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
