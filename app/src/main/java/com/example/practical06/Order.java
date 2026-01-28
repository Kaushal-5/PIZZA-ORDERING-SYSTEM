package com.example.practical06;

/**
 * Model class representing a User's Order.
 */
public class Order {
    public String orderId;
    public String date;
    public double amount;
    public String status;
    public String itemsDescription;

    /**
     * Constructor for Order.
     *
     * @param orderId          Unique ID of the order.
     * @param date             Date of the order.
     * @param amount           Total amount of the order.
     * @param status           Current status of the order.
     * @param itemsDescription Description of items in the order.
     */
    public Order(String orderId, String date, double amount, String status, String itemsDescription) {
        this.orderId = orderId;
        this.date = date;
        this.amount = amount;
        this.status = status;
        this.itemsDescription = itemsDescription;
    }
}
