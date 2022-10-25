package org.imartynov.quote.input;

import org.imartynov.quote.stock.dto.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);
    int counter = 0;
    long prevReportTime = 0;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public Receiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    void report(Quote q) {
        counter++;
        if (prevReportTime == 0)
            prevReportTime = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        if (now > prevReportTime + 5000) {
            LOG.info("Received {} {}", q, counter);
            counter = 0;
            prevReportTime = now;
        }
    }

    @RabbitListener(queues = ("stock_exchange"))
    public void receiveProduct(Quote q) {
        report(q);
        // validatinon and filtration here ...
        // send to fanout exchange
        rabbitTemplate.convertAndSend(QuoteInputApplication.exchangeName, "", q);
    }

}
