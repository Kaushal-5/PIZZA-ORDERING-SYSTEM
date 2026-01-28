package com.example.practical06;

public class Order {
    public String orderId;
    public String date;
    public double amount;
    public String status;
    public String itemsDescription;

    public Order(String orderId, String date, double amount, String status, String itemsDescription) {
        this.orderId = orderId;
        this.date = date;
        this.amount = amount;
        this.status = status;
        this.itemsDescription = itemsDescription;
    }
}
