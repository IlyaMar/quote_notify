package org.imartynov.pet.initializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.imartynov.pet.PetApplication;
import org.imartynov.pet.dao.PetRepository;
import org.imartynov.pet.entity.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeDatabase implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(PetApplication.class);

    private PetRepository petRepository;

    private Forbes400Properties forbes400Properties;

    private final RestTemplate restTemplate;

    @Autowired
    public InitializeDatabase(PetRepository petRepository,
                              Forbes400Properties forbes400Properties, RestTemplate restTemplate) {
        this.petRepository = petRepository;
        this.forbes400Properties = forbes400Properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("refreshing DB...");
        fillDatabaseDuringStartup();
        LOG.info("DB refresh complete...");
    }

    private void fillDatabaseDuringStartup() throws JsonProcessingException {
        List<Pet> pets = new ArrayList<>();
        final ResponseEntity<String> respEntity = restTemplate.getForEntity(forbes400Properties.buildEndPoint(), String.class);
        if (respEntity.getStatusCode().isError()) {
            throw new RuntimeException("Issue with forbes 400 service");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(respEntity.getBody());
        if (jsonNode.isArray()) {
            for (JsonNode petNode : jsonNode) {
                Pet p = new Pet();
                final String petString = petNode.toString();
                String name = JsonPath.read(petString, "$.person.name");
                p.setName(name);
                pets.add(p);
            }
        }
        petRepository.saveAll(pets);
    }


}
