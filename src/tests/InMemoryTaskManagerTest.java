package tests;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    protected Subtask createSubtask(int epicId) {
        return new Subtask("ПОДЗАДАЧА", "ОПИСАНИЕ", epicId);
    }

    protected Epic createEpic() {
        return new Epic("ЭПИК", "ОПИСАНИЕ");
    }

    protected Task createTask() {
        return new Task("ЗАДАЧА", "ОПИСАНИЕ");
    }

    protected TaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldBeAddDifferentTaskAndFindById() {
        Task task = createTask();
        Epic epic = createEpic();
        int taskId = manager.addTask(task).getId();
        int epicId = manager.addEpic(epic).getId();
        Subtask subtask = createSubtask(epicId);
        int subtaskId = manager.addSubTask(subtask).getId();
        assertNotNull(epicId, "ERROR");
        assertNotNull(subtaskId, "ERROR");
        assertEquals(task, manager.getTaskFromId(taskId), "ERROR");
        assertEquals(epic, manager.getEpicFromId(epicId), "ERROR");
        assertEquals(subtask, manager.getSubTaskFromId(subtaskId), "ERROR");
    }

    @Test
    void shouldBeAddSetIdTaskAndGenerationIdTask() {
        int setId = 1;
        Task task1 = createTask();
        Task task2 = createTask();
        int taskId1 = manager.addTask(task1).getId();
        int taskId2 = manager.addTask(task2).getId();
        task1.setId(setId);
        assertEquals(taskId1, task1.getId(), "ERROR");
    }

}