package org.imartynov.quote.input;

import org.imartynov.quote.input.initializers.InputProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableRabbit
@EnableConfigurationProperties(InputProperties.class)
public class QuoteInputApplication {
	private static final Logger LOG = LoggerFactory.getLogger(QuoteInputApplication.class);

	@Autowired
	private InputProperties properties;

	static final String exchangeName = "input";		// exchange where we sent events


//	@Bean
//	FanoutExchange exchange() {
//		return new FanoutExchange(exchangeName);
//	}
//
//	@Bean
//	public Declarables queues() {
//		return new Declarables(
//			IntStream.range(1, properties.getFanoutCount() + 1).boxed().map(n -> String.format("input_%d", n)).map(
//					n -> new Queue(n, false, false, true)).collect(Collectors.toList())
//		);
//	}

	@Bean
	Declarables fanoutBindings() {
		FanoutExchange exchange = new FanoutExchange(exchangeName);
		List<Declarable> res = new LinkedList<>();

		List<Queue> queues = IntStream.range(1, properties.getFanoutCount()).boxed().map(n -> String.format("input_%d", n)).map(
						n -> new Queue(n, false, false, true)).collect(Collectors.toList());

		List<Binding> bindings = queues.stream().map(q -> BindingBuilder.bind(q).to(exchange)).collect(Collectors.toList());
		res.add(exchange);
		res.addAll(queues);
		res.addAll(bindings);
		return new Declarables(res);
	}

//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//											 MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(queueName);
//		container.setMessageListener(listenerAdapter);
//		container.setConcurrency("3-10");
//		return container;
//	}
//

	public static void main(String[] args) {
		// Launch the application
		SpringApplication.run(QuoteInputApplication.class, args);
	}

}
