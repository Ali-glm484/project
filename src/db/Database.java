package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;

public final class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int id = 0;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {};

    public static void add(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);

        e.id = ++id;
        entities.add(e.copy());
    }

    public static Entity get(int id) {
        for(Entity e : entities) {
            if (e.id == id) {
                return e.copy();
            }
        }

        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == id) {
                entities.remove(i);
                return;
            }
        }

        throw new EntityNotFoundException(id);
    }

    public static void update(Entity e) throws InvalidEntityException {
        Validator validator = validators.get(e.getEntityCode());
        validator.validate(e);

        for (int i = 0; i < entities.size(); i++) {
            if (e.id == entities.get(i).id) {
                entities.set(i, e.copy());
                return;
            }
        }

        throw new EntityNotFoundException(e.id);
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.get(entityCode) != null)
            throw new IllegalArgumentException("This validator already exists in the HashMap.");

        validators.put(entityCode, validator);
    }
}
