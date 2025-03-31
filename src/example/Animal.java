package example;

import db.Entity;

public class Animal extends Entity {
    public static final int ANIMAL_ENTITY_CODE = 12;

    public String name;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public Animal copy() {
        Animal copyAnimal = new Animal(name);
        copyAnimal.id = id;

        return copyAnimal;
    }

    @Override
    public int getEntityCode() {
        return ANIMAL_ENTITY_CODE;
    }
}