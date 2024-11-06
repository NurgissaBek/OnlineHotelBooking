package com.example.hotel_booking.models.service;

import com.example.hotel_booking.models.users.User;
import com.example.hotel_booking.models.repository.UserRepository;;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        for (User user : userRepository.getAllUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean authenticateUser(String username, String password) {
        User user = findUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public void registerUser(User user) {
        if (findUserByUsername(user.getUsername()) == null) {
            userRepository.addUser(user);
        } else {
            throw new IllegalArgumentException("Username already exists.");
        }
    }
}
