package example;

import db.Entity;

public class Animal extends Entity {
    public String name;

    public Animal(String name) {
        this.name = name;
    }
}