package example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human))
            throw new IllegalArgumentException("This class is not a subclass of human.");

        if (((Human) entity).age < 0 && ((Human) entity).name == null)
            throw new InvalidEntityException("Age and name must have valid values.");

        if (((Human) entity).age < 0)
            throw new InvalidEntityException("Age must be a positive integer.");

        if (((Human) entity).name == null)
            throw new InvalidEntityException("The name must not be null.");
    }
}
