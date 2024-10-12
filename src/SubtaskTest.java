import org.junit.jupiter.api.Test;
import enums.Status;

import static org.junit.jupiter.api.Assertions.*;


class SubtaskTest {

    @Test
    public void SubtasksWithEqualIdShouldBeEqual() {
        SubTask subtask1 = new SubTask(10, "Купить хлеб", "В Магните у дома", Status.NEW, 5);
        SubTask subtask2 = new SubTask(10, "Купить молоко", "В Пятерочке", Status.DONE, 5);
        assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}