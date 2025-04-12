package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step))
            throw new IllegalArgumentException("This class is not a subclass of step.");

        Database.get(((Step) entity).taskRef);

        if (((Step) entity).title == null)
            throw new InvalidEntityException("Step title cannot be empty.");
    }
}
