package com.example.hotel_booking.controllers;

import com.example.hotel_booking.models.User;
import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.models.service.UserService;
import com.example.hotel_booking.models.service.RoomService;
import com.example.hotel_booking.models.service.BookingFacade; // Corrected import path
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RoomService roomService;
    private final UserService userService;
    private final BookingFacade bookingFacade; // Added BookingFacade as a field

    // Constructor injection for all dependencies, including BookingFacade
    public UserController(RoomService roomService, UserService userService, BookingFacade bookingFacade) {
        this.roomService = roomService;
        this.userService = userService;
        this.bookingFacade = bookingFacade;
    }

    // View all available rooms
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> viewAvailableRooms() {
        try {
            List<Room> availableRooms = roomService.getAvailableRooms();
            return ResponseEntity.ok(availableRooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Book a room
    @PostMapping("/book")
    public ResponseEntity<String> bookRoom(@RequestParam String roomType, @RequestParam String username) {
        boolean bookingResult = bookingFacade.bookRoom(roomType, username); // Corrected BookingFacade usage
        if (bookingResult) {
            return ResponseEntity.ok("Room booked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book the room.");
        }
    }
}
