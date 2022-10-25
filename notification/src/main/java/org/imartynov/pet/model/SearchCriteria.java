package org.imartynov.pet.model;

import javax.validation.constraints.Pattern;

public class SearchCriteria {

    @Pattern(regexp = "(\\w+)")
    private String petName;

    @Pattern(regexp = "/[1-4]/")
    private short rating;

    //getters and setters


    public String getPetName() {
        return petName;
    }

    public short getRating() {
        return rating;
    }
}