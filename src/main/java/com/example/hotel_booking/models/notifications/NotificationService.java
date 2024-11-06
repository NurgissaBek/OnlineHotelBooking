package com.example.hotel_booking.models.notifications;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class NotificationService {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String username, String message) {
        for (Observer observer : observers) {
            if (observer.getUsername().equals(username)) {
                observer.update(message);
            }
        }
    }
}
