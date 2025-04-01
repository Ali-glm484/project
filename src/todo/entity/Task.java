package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public enum Status {
        NotStarted, InProgress, Completed
    }

    private Date creationDate;
    private Date lastModificationDate;
    public static final int TASK_ENTITY_CODE = 10;

    public String title;
    public String description;
    public Date dueDate;
    public Status status;

    public Task(String title, String description, Date dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    @Override
    public Task copy() {
        Task copyTask = new Task(title, description, dueDate, status);
        copyTask.id = id;

        if (this.creationDate != null) {
            copyTask.creationDate = new Date(this.creationDate.getTime());
        }
        if (this.lastModificationDate != null) {
            copyTask.lastModificationDate = new Date(this.lastModificationDate.getTime());
        }

        return copyTask;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
