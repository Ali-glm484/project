package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Task))
            throw new IllegalArgumentException("This class is not a subclass of task.");

        if (((Task) entity).title == null)
            throw new InvalidEntityException("Task title cannot be empty.");
    }
}
