package com.example.hotel_booking.models.rooms;

public class SingleRoom extends Room {
    public SingleRoom() {
        this.roomType = "Single";
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
