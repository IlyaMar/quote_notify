package org.imartynov.quote.stock;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

    public void receiveMessage(String m) {
        System.out.println("Received <" + m + ">");
    }

}