package com.example.hotel_booking.models.rooms;

public class RoomFactory {
    public Room createRoom(String roomType) {
        return switch (roomType.toLowerCase()) {
            case "single" -> new SingleRoom();
            case "double" -> new DoubleRoom();
            // Добавьте другие типы комнат, если есть
            default -> null;
        };
    }
}
