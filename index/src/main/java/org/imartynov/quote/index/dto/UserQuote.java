package org.imartynov.quote.index.dto;

import java.io.Serializable;
import java.util.Arrays;

/*
 Quote for subscribed users
 */
public class UserQuote implements Serializable {
    private String stock;
    private double price;

    private long [] userIds;        // subscribers

    public UserQuote() {
    }

    public UserQuote(String stock, double price, long[] userIds) {
        this.stock = stock;
        this.price = price;
        this.userIds = userIds;
    }

    public String getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public long[] getUserIds() {
        return userIds;
    }

    @Override
    public String toString() {
        return "UserQuote{" +
                "stock='" + stock + '\'' +
                ", price=" + price +
                ", userIds=" + Arrays.toString(userIds) +
                '}';
    }
}