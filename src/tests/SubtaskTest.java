package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import enums.Status;


class SubtaskTest {

    @Test
    public void SubtasksWithEqualIdShouldBeEqual() {
        SubTask subtask1 = new SubTask(10, "Купить хлеб", "В Магните у дома", Status.NEW, 5);
        SubTask subtask2 = new SubTask(10, "Купить молоко", "В Пятерочке", Status.DONE, 5);
        Assertions.assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Tasks.Task должны быть равны друг другу, если равен их id;");
    }
}