package org.imartynov.pet.dao;

import org.imartynov.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findPets(@Param("searchTerm") String searchTerm);

    @Query(value = "SELECT p from Pet p WHERE p.name LIKE :searchTerm LIMIT 1", nativeQuery = true)
    Pet findFirstPet(@Param("searchTerm") String searchTerm);
}