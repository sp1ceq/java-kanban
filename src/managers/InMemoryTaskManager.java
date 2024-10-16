package managers;

import tasks.Epic;
import tasks.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, SubTask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int id = 1;

    private int getNextID() {
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

    private void updateEpicStatus(Epic epic) {
        int count = 0;
        int newCount = 0;
        ArrayList<SubTask> list = epic.getTaskList();

        for (SubTask subtask : list) {
            if (subtask.getStatus() == Status.DONE) {
                count++;
            }
            if (subtask.getStatus() == Status.NEW) {
                newCount++;
            }
        }
        if (count == list.size()) {
            epic.setStatus(Status.DONE);
        } else if (newCount == list.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
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
@Override
public Task getTaskByID(int id) {
    Task task = tasks.get(id);
    historyManager.addTask(task);
    return task;
}
    @Override
    public Epic getEpicByID(int id) {
        Epic epic = epics.get(id);
        historyManager.addEpic(epic);
        return epics.get(id);
    }
@Override
    public SubTask getSubtaskByID(int id) {
    SubTask subTask = subtasks.get(id);
        historyManager.addSubTask(subTask);
        return subtasks.get(id);
    }
@Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }
@Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }
@Override
    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<SubTask> getEpicSubtasks(Epic epic) {
        return epic.getTaskList();
    }
@Override
    void deleteTasks() {
        tasks.clear();
    }
@Override
    void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }
@Override
    void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            epic.setStatus(Status.NEW);
        }
    }
@Override
    void deleteTaskByID(int id) {
        tasks.remove(id);
    }
@Override
    void deleteEpicByID(int id) {
        ArrayList<SubTask> epicSubtasks = epics.get(id).getTaskList();
        epics.remove(id);
        for (SubTask subtask : epicSubtasks) {
            subtasks.remove(subtask.getId());
        }
    }
@Override
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
   public HistoryManager getHistory(HistoryManager){
     return historyManager;
    }
}

