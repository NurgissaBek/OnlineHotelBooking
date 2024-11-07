package com.example.hotel_booking.models.service;

import com.example.hotel_booking.models.rooms.Room;
import com.example.hotel_booking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findAllRooms().stream()
                .filter(Room::isAvailable)
                .collect(Collectors.toList());
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public boolean removeRoom(int roomId) {
        return roomRepository.deleteRoomById(roomId);
    }
}
