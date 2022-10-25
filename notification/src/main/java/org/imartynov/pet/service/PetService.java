package org.imartynov.pet.service;

import org.imartynov.pet.dao.PetRepository;
import org.imartynov.pet.entity.Pet;
import org.imartynov.pet.model.PetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    private static final Logger LOG = LoggerFactory.getLogger(PetService.class);

    private PetRepository petRepository;

    @Autowired
    public void setPetRepository(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<PetDTO> searchPet(String searchTerm) {
        LOG.info("Searching term {}", searchTerm);
        List<PetDTO> result = petRepository.findPets(buildLikePattern(searchTerm)).stream().map(c -> new PetDTO(c.getId(), c.getName())).collect(Collectors.toList());
        LOG.info("Search Result: {} ", result);
        return result;
    }

    private String buildLikePattern(String searchTerm) {
        return searchTerm.toLowerCase() + "%";
    }

    public List<PetDTO> getAll() {
        return petRepository.findAll().stream().map(c -> new PetDTO(c.getId(), c.getName())).collect(Collectors.toList());
    }

    public PetDTO getById(long id) {
        Pet p = petRepository.getReferenceById(id);
        return new PetDTO(p.getId(), p.getName());
    }

    public PetDTO updatePet(long id, PetDTO pet) {
        Pet p = petRepository.getReferenceById(id);
        p.setName(pet.getName());
        petRepository.save(p);
        return pet;
    }

    public void addPet(String... names) {
        for (String name : names) {
            Pet pet = new Pet();
            pet.setName(name);
            petRepository.save(pet);
        }
    }

    public void addPet(PetDTO pet) {
        Pet p = new Pet();
        p.setName(pet.getName());
        petRepository.save(p);
    }

}