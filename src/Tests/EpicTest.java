package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import enums.Status;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic(10, "Сделать ремонт", "Уложиться в 2 миллиона", Status.NEW);
        Epic epic2 = new Epic(10, "Подготовиться к собеседованию", "1 июля в 11:00",
                Status.IN_PROGRESS);
        Assertions.assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Tasks.Task должны быть равны друг другу, если равен их id;");
    }
}