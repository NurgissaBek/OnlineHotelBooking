package com.example.hotel_booking.models.service;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.repository.RoomRepository;

import java.util.List;

public class RoomBookingService {

    private RoomRepository roomRepository;

    public RoomBookingService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findRoom(String roomType) {
        List<Room> rooms = roomRepository.findAllRooms();
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(roomType) && room.isAvailable()) {
                room.book();
                roomRepository.updateRoomAvailability(room.getRoomId(), false);
                return room;
            }
        }
        return null;
    }

    public boolean cancelBooking(int roomId) {
        Room room = roomRepository.findAllRooms().stream()
                .filter(r -> r.getRoomId() == roomId)
                .findFirst()
                .orElse(null);
        if (room != null && !room.isAvailable()) {
            room.cancel();
            roomRepository.updateRoomAvailability(roomId, true);
            return true;
        }
        return false;
    }
}
