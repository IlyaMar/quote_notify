package org.imartynov.pet;

import org.imartynov.pet.initializers.Forbes400Properties;
import org.imartynov.pet.model.PetDTO;
import org.imartynov.pet.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@SpringBootApplication
//@Import(SwaggerConfig.class)
@EnableConfigurationProperties(Forbes400Properties.class)
public class PetApplication implements ApplicationRunner {
	private static final Logger LOG = LoggerFactory.getLogger(PetApplication.class);
	@Autowired
	private PetService petService;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(3000))
				.setReadTimeout(Duration.ofMillis(3000))
				.build();
	}


	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		petService.addPet("Bobby", "Milk", "Sony");
		List<PetDTO> pets = petService.searchPet("Mi");
		LOG.info("Search Result : {}",pets);
	}


}
