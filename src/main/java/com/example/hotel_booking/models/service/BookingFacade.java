package com.example.hotel_booking.models.service;

import com.example.hotel_booking.models.notifications.NotificationService;
import com.example.hotel_booking.models.payments.PaymentStrategy;
import com.example.hotel_booking.models.rooms.Room;

public class BookingFacade {

    private RoomBookingService roomBookingService;
    private NotificationService notificationService;

    public BookingFacade(RoomBookingService roomBookingService, NotificationService notificationService) {
        this.roomBookingService = roomBookingService;
        this.notificationService = notificationService;
    }

    public String bookRoom(String roomType, PaymentStrategy paymentMethod, String username) {
        Room room = roomBookingService.findRoom(roomType);
        if (room != null) {
            paymentMethod.pay(room.getCost());
            notificationService.notifyObservers(username, "Booking confirmed for " + room.getDescription());
            return "Booking confirmed for " + room.getDescription();
        }
        return "Room not available.";
    }
}
