package org.imartynov.quote.index;

import org.imartynov.quote.index.initializers.IndexProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableRabbit
@EnableConfigurationProperties(IndexProperties.class)
public class QuoteIndexApplication {
	private static final Logger LOG = LoggerFactory.getLogger(QuoteIndexApplication.class);

	@Autowired
	private IndexProperties properties;

	static final String exchangeName = "user_quote";		// exchange where we sent events

	static final String queueName = "user_quote";


	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchangeName);
	}

	@Bean
	public Queue queue() {
		return new Queue(queueName, false, false, true);
	}

	String getInputQueueName(int number) {
		return String.format("input_%d", number);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(getInputQueueName(properties.getInputQueueNumber()));
		container.setMessageListener(listenerAdapter);
		container.setConcurrency("3-10");
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}


	public static void main(String[] args) {
		// Launch the application
		SpringApplication.run(QuoteIndexApplication.class, args);
	}

}
