package com.example.hotel_booking.models.rooms;

public class DoubleRoom extends Room {
    public DoubleRoom() {
        this.roomType = "Double";
    }

    @Override
    public void book() {
        this.isAvailable = false;
    }

    @Override
    public void cancel() {
        this.isAvailable = true;
    }
}
