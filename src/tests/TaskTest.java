package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import enums.Status;

class TaskTest {

    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Task(10, "Купить хлеб", "В Дикси у дома", Status.NEW);
        Task task2 = new Task(10, "Купить молоко", "В Пятерочке", Status.DONE);
        Assertions.assertEquals(task1, task2,
                "Ошибка! Экземпляры класса Tasks.Task должны быть равны друг другу, если равен их id;");
    }
}