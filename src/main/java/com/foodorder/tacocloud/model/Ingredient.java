package com.foodorder.tacocloud.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;

    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Ingredient(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Ingredient() {
    }

    public enum Type {
        WRAP,
        PROTEIN,
        VEGGIES,
        CHEESE,
        SAUCE
    }
}
