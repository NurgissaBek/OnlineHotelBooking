package com.example.hotel_booking.models.rooms;

public class RoomFactory {
    public Room createRoom(String roomType) {
        if ("Single".equalsIgnoreCase(roomType)) {
            return new SingleRoom();
        } else if ("Double".equalsIgnoreCase(roomType)) {
            return new DoubleRoom();
        }
        return null;
    }
}
