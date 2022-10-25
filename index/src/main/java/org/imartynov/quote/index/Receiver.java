package org.imartynov.quote.index;

import org.imartynov.quote.index.dto.UserQuote;
import org.imartynov.quote.index.initializers.IndexProperties;
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
    private IndexProperties properties;

    private IndexService indexService;

    @Autowired
    public Receiver(RabbitTemplate rabbitTemplate, IndexProperties properties, IndexService indexService) {
        this.properties = properties;
        this.rabbitTemplate = rabbitTemplate;
        this.indexService = indexService;
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

    public void receiveProduct(Quote q) {
        report(q);
        // index search here
        UserQuote uq = new UserQuote(q.getStock(), q.getPrice(), indexService.findSubscribedUsers(q.getStock()));
        rabbitTemplate.convertAndSend(QuoteIndexApplication.exchangeName, "", uq);
    }

}
