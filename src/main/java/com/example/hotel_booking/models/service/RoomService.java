package com.example.hotel_booking.models.service;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.repository.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Fetch all available rooms
    public List<Room> getAvailableRooms() {
        // Filter rooms to only include available ones
        return roomRepository.findAllRooms().stream()
                .filter(Room::isAvailable) // Check availability of each room
                .collect(Collectors.toList());
    }
}
