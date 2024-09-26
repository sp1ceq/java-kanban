import java.util.*;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SubTask> subtasks = new HashMap<>();

    private int id = 1;

    int getNextID() {
        return id++;
    }

    public Task addTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    void updateEpicStatus(Epic epic) {
        int count = 0;
        int newCount = 0;
        ArrayList<SubTask> list = epic.getTaskList();

        for (SubTask subtask : list) {
            if (subtask.getStatus() == TaskStatus.DONE) {
                count++;
            }
            if (subtask.getStatus() == TaskStatus.NEW) {
                newCount++;
            }
        }
        if (count == list.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (newCount == list.size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public SubTask addSubtask(SubTask subtask) {
        subtask.setId(getNextID());
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic);
        return subtask;
    }

    public Task updateTask(Task task) {
        int taskID = task.getId();
        if ( !tasks.containsKey(taskID)) {
            return null;
        }
        tasks.replace(taskID, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        int epicID = epic.getId();
        if ( !epics.containsKey(epicID)) {
            return null;
        }
        Epic oldEpic = epics.get(epicID);
        ArrayList<SubTask> oldEpicSubtaskList = oldEpic.getTaskList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (SubTask subtask : oldEpicSubtaskList) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.replace(epicID, epic);
        ArrayList<SubTask> newEpicSubtaskList = epic.getTaskList();
        if (!newEpicSubtaskList.isEmpty()) {
            for (SubTask subtask : newEpicSubtaskList) {
                subtasks.put(subtask.getId(), subtask);
            }
        }
        updateEpicStatus(epic);
        return epic;
    }

    public SubTask updateSubtask(SubTask subtask) {
        int subtaskID = subtask.getId();
        if (subtaskID == 0 || !subtasks.containsKey(subtaskID)) {
            return null;
        }
        int epicID = subtask.getEpicID();
        SubTask oldSubtask = subtasks.get(subtaskID);
        subtasks.replace(subtaskID, subtask);
        Epic epic = epics.get(epicID);
        ArrayList<SubTask> subtaskList = epic.getTaskList();
        subtaskList.remove(oldSubtask);
        subtaskList.add(subtask);
        epic.setSubtaskList(subtaskList);
        updateEpicStatus(epic);
        return subtask;
    }

    public Task getTaskByID(int id) {
        return tasks.get(id);
    }

    public Epic getEpicByID(int id) {
        return epics.get(id);
    }

    public SubTask getSubtaskByID(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<SubTask> getEpicSubtasks(Epic epic) {
        return epic.getTaskList();
    }

    void deleteTasks() {
        tasks.clear();
    }

    void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            epic.setStatus(TaskStatus.NEW);
        }
    }

    void deleteTaskByID(int id) {
        tasks.remove(id);
    }

    void deleteEpicByID(int id) {
        ArrayList<SubTask> epicSubtasks = epics.get(id).getTaskList();
        epics.remove(id);
        for (SubTask subtask : epicSubtasks) {
            subtasks.remove(subtask.getId());
        }
    }

    void deleteSubtaskByID(int id) {
        SubTask subtask = subtasks.get(id);
        int epicID = subtask.getEpicID();
        subtasks.remove(id);
        Epic epic = epics.get(epicID);
        ArrayList<SubTask> subtaskList = epic.getTaskList();
        subtaskList.remove(subtask);
        epic.setSubtaskList(subtaskList);
        updateEpicStatus(epic);
    }
}