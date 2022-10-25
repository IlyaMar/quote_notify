package org.imartynov.pet.entity;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({@NamedQuery(name="Pet.findPets",query = "SELECT p from Pet p WHERE lower(p.name) LIKE :searchTerm ORDER BY p.name")})
@Entity
@Table(name = "pet")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String petName) {
        this.name = petName;
    }
}
