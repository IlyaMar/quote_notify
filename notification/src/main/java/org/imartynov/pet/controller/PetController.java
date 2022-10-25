package org.imartynov.pet.controller;

import org.imartynov.pet.entity.Pet;
import org.imartynov.pet.model.PetDTO;
import org.imartynov.pet.model.SearchCriteria;
import org.imartynov.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PetController {

    private PetService petService;

    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
    }

    @PostMapping(path = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewPet(@RequestBody PetDTO petDTO) {
        petService.addPet(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping(path = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetDTO> pets() {
        return petService.getAll();
    }

    @GetMapping(path = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDTO petWithId(@PathVariable long id) {
        return petService.getById(id);
    }

    @GetMapping(path = "/pet/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PetDTO> searchPet(@Valid SearchCriteria c) {
        return petService.searchPet(c.getPetName());
    }

    @PutMapping(path = "/club/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDTO updatePet(@PathVariable long id, @RequestBody PetDTO petDTO) {
        return petService.updatePet(id, petDTO);
    }
}
