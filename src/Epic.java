import java.util.*;

public class Epic extends Task {

    ArrayList<SubTask> taskList = new ArrayList<>();

    public Epic(String text, String description) {
        super(text, description);
    }

    public Epic(int id, String text, String description, TaskStatus status) {
        super(id, text, description, status);
    }

    void addSubtask(SubTask subtask) {
        taskList.add(subtask);
    }

    void clearSubtasks() {
        taskList.clear();
    }

    void setSubtaskList(ArrayList<SubTask> subtaskList) {
        this.taskList = subtaskList;
    }

    ArrayList<SubTask> getTaskList() {
        return taskList;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name= " + getName() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", subtaskList.size = " + taskList.size() +
                ", status = " + getStatus() +
                '}';
    }
}
