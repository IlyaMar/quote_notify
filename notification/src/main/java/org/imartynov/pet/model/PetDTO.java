package org.imartynov.pet.model;

import java.io.Serializable;

public class PetDTO implements Serializable {
    private final String name;

    private final long id;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public PetDTO(long id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "PetDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}