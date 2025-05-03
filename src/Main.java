import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf(
                    "1) add task%n" +
                            "2) add step%n" +
                            "3) delete%n" +
                            "4) update task%n" +
                            "5) update step%n" +
                            "6) get task by id%n" +
                            "7) get all tasks%n" +
                            "8) get incomplete tasks%n" +
                            "9) exit%n" +
                            "Enter command: "
            );

            String command = scanner.nextLine().trim().toLowerCase();

            try {
                switch (command) {
                    case "add task": {
                        System.out.print("Title: ");
                        String title = scanner.nextLine().trim();

                        System.out.print("Description: ");
                        String description = scanner.nextLine().trim();

                        System.out.print("Due date (yyyy-MM-dd): ");
                        String inputDate = scanner.nextLine().trim();
                        Date dueDate;
                        try {
                            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate ld = LocalDate.parse(inputDate, fmt);
                            dueDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                            break;
                        }

                        TaskService.addTask(title, description, dueDate);
                        break;
                    }
                    case "add step": {
                        System.out.print("TaskID: ");
                        int taskRef;
                        try {
                            taskRef = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid numeric TaskID.");
                            break;
                        }
                        try {
                            Entity e = Database.get(taskRef);
                            if (!(e instanceof Task)) throw new EntityNotFoundException(taskRef);
                        } catch (EntityNotFoundException e) {
                            System.out.println(e.getMessage());
                            break;
                        }

                        System.out.print("Title: ");
                        String title = scanner.nextLine().trim();
                        StepService.addStep(title, taskRef);
                        break;
                    }
                    case "delete": {
                        System.out.print("ID: ");
                        int id;
                        try {
                            id = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid numeric ID.");
                            break;
                        }
                        Entity e;
                        try {
                            e = Database.get(id);
                        } catch (EntityNotFoundException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                        if (e instanceof Task)
                            TaskService.deleteTask(id);
                        else
                            StepService.deleteStep(id);
                        break;
                    }
                    case "update task": {
                        System.out.print("ID: ");
                        int id;
                        try {
                            id = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid numeric ID.");
                            break;
                        }
                        try {
                            Entity e = Database.get(id);
                            if (!(e instanceof Task)) throw new EntityNotFoundException(id);
                        } catch (EntityNotFoundException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }

                        System.out.print("Field (title, description, due date, status): ");
                        String field = scanner.nextLine().trim().toLowerCase();
                        System.out.print("New Value: ");
                        String newValue = scanner.nextLine().trim().toLowerCase();

                        if (!field.equals("title") && !field.equals("description") && !field.equals("due date") && !field.equals("status")) {
                            System.out.println("Invalid field. Valid fields: title, description, due date, status.");
                            break;
                        }
                        TaskService.updateTask(id, field, newValue);
                        break;
                    }
                    case "update step": {
                        System.out.print("ID: ");
                        int id;
                        try {
                            id = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid numeric ID.");
                            break;
                        }
                        try {
                            Entity e = Database.get(id);
                            if (!(e instanceof Step)) throw new EntityNotFoundException(id);
                        } catch (EntityNotFoundException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }

                        System.out.print("Field (title, taskref, status): ");
                        String field = scanner.nextLine().trim().toLowerCase();
                        System.out.print("New Value: ");
                        String newValue = scanner.nextLine().trim().toLowerCase();

                        if (!field.equals("title") && !field.equals("taskref") && !field.equals("status")) {
                            System.out.println("Invalid field. Valid: title, taskref, status.");
                            break;
                        }
                        StepService.updateStep(id, field, newValue);
                        break;
                    }
                    case "get task by id": {
                        System.out.print("ID: ");
                        int id;
                        try {
                            id = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid numeric ID.");
                            break;
                        }
                        TaskService.getTaskById(id);
                        break;
                    }
                    case "get all tasks":
                        TaskService.getAllTasks();
                        break;
                    case "get incomplete tasks":
                        TaskService.incompleteTasks();
                        break;
                    case "exit":
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Unknown command. Please enter a valid command.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
