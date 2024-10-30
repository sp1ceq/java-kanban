package tests;

import manager.InMemoryHistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class InMemoryHistoryManagerTest {
    private static TaskManager taskManager;
    private InMemoryHistoryManager historyManager;
    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldReturnOldTaskAfterUpdate() {
        Task washFloor = new Task("Помыть полы", "С новым средством");
        taskManager.addTask(washFloor);
        taskManager.getTaskFromId(washFloor.getId());
        taskManager.updateTask(new Task(washFloor.getId(), "Не забыть помыть полы",
                "Можно и без средства", Status.IN_PROGRESS));
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(washFloor.getTitle(),
                oldTask.getTitle(), "В истории не сохранилась старая версия задачи");
        assertEquals(washFloor.getDescription(),
                oldTask.getDescription(), "В истории не сохранилась старая версия задачи");
    }

    @Test
    void addNewTask() {
        Task task = taskManager.addTask(new Task("Test addNewTask", "Test addNewTask description"));
        Task savedTask = taskManager.getTaskFromId(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        List<Task> tasks = taskManager.getListOfTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    /////// new

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddAndRetrieveTasks() {
        Task task1 = new Task(1, "Task 1", "Description 1");
        Task task2 = new Task(2, "Task 2", "Description 2");

        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> tasks = historyManager.getHistory();
        assertEquals(2, tasks.size());
        assertEquals(List.of(task1, task2), tasks);
    }

    @Test
    public void testRemoveTask() {
        Task task1 = new Task(1, "Task 1", "Description 1");
        Task task2 = new Task(2, "Task 2", "Description 2");

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> tasks = historyManager.getHistory();
        assertEquals(1, tasks.size());
        assertEquals(task2, tasks.get(0));
    }

    @Test
    public void testRemoveNonExistentTask() {
        Task task1 = new Task(1, "Task 1", "Description 1");
        historyManager.add(task1);
        historyManager.remove(2); // попробуем удалить несуществующий id

        List<Task> tasks = historyManager.getHistory();
        assertEquals(1, tasks.size());
        assertEquals(task1, tasks.get(0)); // задача 1 все еще должна быть
    }

    @Test
    public void testIntegrityAfterTaskRemoval() {
        Task task1 = new Task(1, "Task 1", "Description 1");
        Task task2 = new Task(2, "Task 2", "Description 2");

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> tasks = historyManager.getHistory();
        assertFalse(tasks.stream().anyMatch(task -> task.getId() == 1), "Задача 1 не должна оставаться в истории");
    }

    @Test
    public void testTaskUpdateIntegrity() {
        Task task = new Task(1, "Initial Task", "Initial Description", Status.NEW);
        historyManager.add(task);

        task.setTitle("Updated Task"); // Обновляем название задачи
        task.setDescription("Updated Description"); // Обновляем описание задачи

        List<Task> tasks = historyManager.getHistory();
        assertEquals(1, tasks.size());
        assertEquals("Updated Task", tasks.get(0).getTitle());
        assertEquals("Updated Description", tasks.get(0).getDescription());
    }
    @Test
    public void testSubtaskInEpicIntegrity() {
        // Создаем эпик и подзадачи
        Epic epic = new Epic("1", "Epic Task");
        Subtask subtask1 = new Subtask("2", "Subtask 1", "Description");
        Subtask subtask2 = new Subtask("3", "Subtask 2", "Description");

        // Добавляем подзадачи в эпик
        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        // Удаляем одну подзадачу
        epic.removeSubtask(2);

        assertFalse(epic.getSubtasks().contains(subtask1), "Подзадача не должна храниться внутри эпика.");
    }
}