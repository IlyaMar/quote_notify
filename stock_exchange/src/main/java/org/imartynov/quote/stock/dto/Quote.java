package org.imartynov.quote.stock.dto;

import java.io.Serializable;

/*
 Incoming event from stock exchange
 */
public class Quote implements Serializable {
    private String stock;
    private double price;

    public Quote() {
    }

    public Quote(String stock, double price) {
        this.stock = stock;
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "stock='" + stock + '\'' +
                ", price=" + price +
                '}';
    }
}