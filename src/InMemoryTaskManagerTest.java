import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import enums.Status;
import task.Task;
import task.SubTask;
import task.Epic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask() {
        //проверяем, что InMemoryTaskManager добавляет задачи и может найти их по id;
        final Task task = taskManager.addTask(new Task("Test addNewTask", "Test addNewTask description"));
        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpicAndSubtasks() {
        //проверяем, что InMemoryTaskManager добавляет эпики и подзадачи и может найти их по id;
        final Epic flatRenovation = taskManager.addEpic(new Epic("Сделать ремонт",
                "Нужно успеть за отпуск"));
        final SubTask flatRenovationSubtask1 = taskManager.addSubtask(new SubTask("Поклеить обои",
                "Обязательно светлые!", flatRenovation.getId()));
        final SubTask flatRenovationSubtask2 = taskManager.addSubtask(new SubTask("Установить новую технику",
                "Старую продать на Авито", flatRenovation.getId()));
        final SubTask flatRenovationSubtask3 = taskManager.addSubtask(new SubTask("Заказать книжный шкаф", "Из темного дерева",
                flatRenovation.getId()));
        final Epic savedEpic = taskManager.getEpicByID(flatRenovation.getId());
        final SubTask savedSubtask1 = taskManager.getSubtaskByID(flatRenovationSubtask1.getId());
        final SubTask savedSubtask2 = taskManager.getSubtaskByID(flatRenovationSubtask2.getId());
        final SubTask savedSubtask3 = taskManager.getSubtaskByID(flatRenovationSubtask3.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertEquals(flatRenovation, savedEpic, "Эпики не совпадают.");
        assertEquals(flatRenovationSubtask1, savedSubtask1, "Подзадачи не совпадают.");
        assertEquals(flatRenovationSubtask3, savedSubtask3, "Подзадачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(flatRenovation, epics.getFirst(), "Эпики не совпадают.");

        final List<SubTask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("имя", "описание");
        taskManager.addTask(expected);
        final Task updatedTask = new Task(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулась задачи с другим id");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("имя", "описание");
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("имя", "описание");
        taskManager.addEpic(epic);
        final SubTask expected = new SubTask("имя", "описание", epic.getId());
        taskManager.addSubtask(expected);
        final SubTask updatedSubtask = new SubTask(expected.getId(), "новое имя", "новое описание",
                Status.DONE, epic.getId());
        final SubTask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

    @Test
    public void deleteTasksShouldReturnEmptyList() {
        taskManager.addTask(new Task("Купить книги", "Список в заметках"));
        taskManager.addTask(new Task("Помыть полы", "С новым средством"));
        taskManager.deleteTasks();
        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty(), "После удаления задач список должен быть пуст.");
    }

    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic("Сделать ремонт", "Нужно успеть за отпуск"));
        taskManager.deleteEpics();
        List<Epic> epics = taskManager.getEpics();
        assertTrue(epics.isEmpty(), "После удаления эпиков список должен быть пуст.");
    }

    @Test
    public void deleteSubtasksShouldReturnEmptyList() {
        Epic flatRenovation = new Epic("Сделать ремонт", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new SubTask("Поклеить обои", "Обязательно светлые!",
                flatRenovation.getId()));
        taskManager.addSubtask(new SubTask("Установить новую технику", "Старую продать на Авито",
                flatRenovation.getId()));
        taskManager.addSubtask(new SubTask("Заказать книжный шкаф", "Из темного дерева",
                flatRenovation.getId()));

        taskManager.deleteSubtasks();
        List<SubTask> subtasks = taskManager.getSubtasks();
        assertTrue(subtasks.isEmpty(), "После удаления подзадач список должен быть пуст.");
    }

    @Test
    public void deleteTaskByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addTask(new Task(1, "Купить книги", "Список в заметках", Status.NEW));
        taskManager.addTask(new Task(2, "Помыть полы", "С новым средством", Status.DONE));
        assertNull(taskManager.deleteTaskByID(3));
    }

    @Test
    public void deleteEpicByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addEpic(new Epic(1, "Сделать ремонт", "Нужно успеть за отпуск", Status.IN_PROGRESS));
        taskManager.deleteEpicByID(1);
        assertNull(taskManager.deleteTaskByID(1));
    }

    @Test
    public void deleteSubtaskByIdShouldReturnNullIfKeyIsMissing() {
        Epic flatRenovation = new Epic("Сделать ремонт", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new SubTask("Поклеить обои", "Обязательно светлые!",
                flatRenovation.getId()));
        taskManager.addSubtask(new SubTask("Установить новую технику", "Старую продать на Авито",
                flatRenovation.getId()));
        taskManager.addSubtask(new SubTask("Заказать книжный шкаф", "Из темного дерева",
                flatRenovation.getId()));
        assertNull(taskManager.deleteSubtaskByID(5));
    }


    @Test
    void TaskCreatedAndTaskAddedShouldHaveSameVariables() {
        Task expected = new Task(1, "Помыть полы", "С новым средством", Status.DONE);
        taskManager.addTask(expected);
        List<Task> list = taskManager.getTasks();
        Task actual = list.getFirst();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

}