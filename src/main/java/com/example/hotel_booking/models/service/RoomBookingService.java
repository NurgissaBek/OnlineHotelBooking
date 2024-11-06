package com.example.hotel_booking.models.service;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.models.repository.RoomRepository;

public class RoomBookingService {
    private RoomRepository roomRepository;

    public RoomBookingService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findRoom(String roomType) {
        for (Room room : roomRepository.getAllRooms()) {
            if (room.getType().equalsIgnoreCase(roomType) && !room.isBooked()) {
                room.setBooked(true); // Mark the room as booked
                return room;
            }
        }
        return null;
    }

    public boolean cancelBooking(String roomType) {
        for (Room room : roomRepository.getAllRooms()) {
            if (room.getType().equalsIgnoreCase(roomType) && room.isBooked()) {
                room.setBooked(false); // Unmark the room as booked
                return true;
            }
        }
        return false;
    }
}
