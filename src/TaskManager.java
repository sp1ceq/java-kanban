import java.util.List;


public interface TaskManager {
    int getNextID();

    Task addTask(Task task);

    Epic addEpic(Epic epic);

    SubTask addSubtask(SubTask subtask);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    SubTask updateSubtask(SubTask subtask);

    Task getTaskByID(int id);

    Epic getEpicByID(int id);

    SubTask getSubtaskByID(int id);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubtasks();

    List<SubTask> getEpicSubtasks(Epic epic);

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    Task deleteTaskByID(int id);

    Epic deleteEpicByID(int id);

    SubTask deleteSubtaskByID(int id);

    List<Task> getHistory();
}