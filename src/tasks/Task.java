package tasks;

import java.util.*;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name , String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

     void setDescription(String description) {
        this.description = description;
    }

     String getDescription() {
        return description;
    }

     void setId(int id) {
        this.id = id;
    }

     int getId() {
        return id;
    }

     void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Tasks.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
