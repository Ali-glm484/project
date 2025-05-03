package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StepService {
    public static void addStep(String title, int taskRef) {
        Step newStep = new Step(title, Step.Status.NotStarted, taskRef);

        try {
            Database.add(newStep);

            Date creationDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            System.out.println("Step saved successfully.");
            System.out.println("ID: " + newStep.id);
            System.out.println("Creation Date: " + dateFormat.format(creationDate));
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteStep(int id) {
        try {
            Database.delete(id);
            System.out.println("Entity with ID = " + id + " successfully deleted.");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Error : something happened.");
        }
    }

    public static void updateStep(int id, String field, String newValue) {
        Step step = (Step) Database.get(id);
        Step updatedStep = null;
        String oldValue;

        if (field.equals("title")) {
            updatedStep = new Step(newValue, step.status, step.taskRef);

            oldValue = step.title;

        } else if (field.equals("taskref")) {
            updatedStep = new Step(step.title, step.status, Integer.parseInt(newValue));

            oldValue = Integer.toString(step.taskRef);

        } else {
            if (newValue.equals("not started")) {
                updatedStep = new Step(step.title, Step.Status.NotStarted, step.taskRef);

            } else {
                updatedStep = new Step(step.title, Step.Status.Completed, step.taskRef);

                int flagCompleted = 1;
                for (Entity entity : Database.getAll(Step.STEP_ENTITY_CODE)) {
                    if (((Step) entity).taskRef == step.taskRef) {
                        if (((Step) entity).status != Step.Status.Completed) {
                            flagCompleted = 0;
                            break;
                        }
                    }
                }

                if (flagCompleted == 1)
                    TaskService.updateTask(step.taskRef, "status", "completed");
                else
                    TaskService.updateTask(step.taskRef, "status", "in progress");
            }

            oldValue = step.status.toString();
        }

        try {
            updatedStep.id = id;
            Database.update(updatedStep);

            Date motificationDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            System.out.println("Successfully updated the step.");
            System.out.println("Field: " + field);
            System.out.println("Old Value: " + oldValue);
            System.out.println("New Value: " + newValue);
            System.out.println("Modification Date: " + dateFormat.format(motificationDate));
        } catch (InvalidEntityException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Cannot update step with ID = " + id + ".");
            System.out.println("Error: Cannot find entity with id = " + id + ".");
        }
    }
}