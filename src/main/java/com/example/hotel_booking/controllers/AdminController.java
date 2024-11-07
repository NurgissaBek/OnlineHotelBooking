package com.example.hotel_booking.controllers;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.models.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final RoomService roomService;

    public AdminController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        try {
            roomService.addRoom(room);
            return ResponseEntity.ok("Room added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add room.");
        }
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        boolean result = roomService.removeRoom(roomId);
        if (result) {
            return ResponseEntity.ok("Room deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room deletion failed.");
    }
}
