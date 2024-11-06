package com.example.hotel_booking.models.notifications;

public interface Observer {
    void update(String message);
    String getUsername();
}
