package com.example.hotel_booking.models.payments;

import java.io.Serializable;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " on credit card");
    }
}
