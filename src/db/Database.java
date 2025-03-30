package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;

public final class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int id = 0;

    public static void add(Entity e) {
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

    public static void update(Entity e) {
        for (int i = 0; i < entities.size(); i++) {
            if (e.id == entities.get(i).id) {
                entities.set(i, e.copy());
                return;
            }
        }

        throw new EntityNotFoundException(e.id);
    }
}
