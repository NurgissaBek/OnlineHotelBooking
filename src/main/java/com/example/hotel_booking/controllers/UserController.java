package com.example.hotel_booking.controllers;

import com.example.hotel_booking.models.User;
import com.example.hotel_booking.models.payments.PaymentStrategy;
import com.example.hotel_booking.models.payments.CreditCardPayment;
import com.example.hotel_booking.models.payments.PayPalPayment;
import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.models.service.UserService;
import com.example.hotel_booking.models.service.RoomService;
import com.example.hotel_booking.models.service.BookingFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RoomService roomService;
    private final UserService userService;
    private final BookingFacade bookingFacade;

    public UserController(RoomService roomService, UserService userService, BookingFacade bookingFacade) {
        this.roomService = roomService;
        this.userService = userService;
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> viewAvailableRooms() {
        try {
            List<Room> availableRooms = roomService.getAvailableRooms();
            return ResponseEntity.ok(availableRooms);
        } catch (Exception e) {
            e.printStackTrace(); // Log error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // Log error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookRoom(
            @RequestParam String roomType,
            @RequestParam String username,
            @RequestParam String paymentMethod) {

        try {
            // Select payment strategy based on the input
            PaymentStrategy strategy;
            if ("PayPal".equalsIgnoreCase(paymentMethod)) {
                strategy = new PayPalPayment();
            } else {
                strategy = new CreditCardPayment();
            }

            String bookingResult = bookingFacade.bookRoom(roomType, strategy, username);

            if (bookingResult.contains("Booking confirmed")) {
                return ResponseEntity.ok(bookingResult);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book the room.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while booking the room.");
        }
    }
}
