package com.example.hotel_booking.models.rooms;

public abstract class Room {
    protected int roomId;
    protected String roomType;
    protected double pricePerNight;
    protected boolean isAvailable;
    protected int capacity;

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public abstract void book();
    public abstract void cancel();

    public double getCost() {
        return this.pricePerNight;
    }

    public String getDescription() {
        return "Room Type: " + roomType + ", Capacity: " + capacity + ", Price: $" + pricePerNight + " per night";
    }
}
