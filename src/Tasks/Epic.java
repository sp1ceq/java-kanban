package Tasks;

import java.util.*;

public class Epic extends Task {

    ArrayList<SubTask> taskList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name  , description);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
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
        return "Tasks.Epic{" +
                "name= " + getName() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", subtaskList.size = " + taskList.size() +
                ", status = " + getStatus() +
                '}';
    }
}
