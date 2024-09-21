import java.util.*;

public class Task {
    private String text;
    private String description;
    private int id;
    private TaskStatus status;

    public Task(int id, String text, String description, TaskStatus status) {
        this.id = id;
        this.text = text;
        this.description = description;
        this.status = status;
    }

    public Task(String text, String description) {
        this.text = text;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    void setName(String text) {
        this.text = text;
    }

    public String getName() {
        return text;
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

     void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id && Objects.equals(text, task.text) && Objects.equals(description, task.description)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (text != null) {
            hash = hash + text.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + text + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
