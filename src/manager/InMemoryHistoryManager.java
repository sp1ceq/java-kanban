package manager;

import tasks.Task;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryHistoryManager implements HistoryManager {


    // Создает хеш-таблицу (словарь),
    // где ключ — это Integer (ID задачи),
    // а значение — узел (Node) типа Task.
    // Это будет использоваться для быстрого доступа к узлам задач по их идентификатору.
    private final HashMap<Integer, Node<Task>> receivedTasks = new HashMap<>();

    // - Объявляет два поля: head (голова) и tail (хвост) типа Node<Task>.
// Эти поля будут использоваться для отслеживания начала и конца двусвязного списка задач.
    private Node<Task> head;
    private Node<Task> tail;


    // Переопределяет метод add из интерфейса HistoryManager.
    // Этот метод будет добавлять новую задачу в историю.
    @Override
    public void add(Task task) {
        if (!(task == null)) { //- Проверяет, что переданная задача не равна null. Это предотвращает добавление пустых задач.
            remove(task.getId()); // - Удаляет задачу из истории, если она уже была добавлена (чтобы удалить старую версию).
            linkLast(task); // - Вызывает метод linkLast, чтобы добавить новую задачу в конец двусвязного списка.
        }
    }


    //Переопределяет метод remove из интерфейса HistoryManager, который удаляет задачу по её идентификатору.
    @Override
    public void remove(int id) {
        removeNode(receivedTasks.get(id)); //- Получает узел задачи из хеш-таблицы по её ID и вызывает метод removeNode, чтобы удалить этот узел из списка.
    }

    //  Переопределяет метод getHistory из интерфейса HistoryManager, который возвращает список задач в истории просмотров.
    @Override
    public List<Task> getHistory() {
        return getTasks(); // - Вызывает метод getTasks, чтобы получить список задач из двусвязного списка, и возвращает его.
    }


    //- Объявляет метод linkLast, который добавляет новый узел с задачей в конец двусвязного списка.
    private void linkLast(Task element) {
        final Node<Task> oldTail = tail; // - Сохраняет ссылку на текущий хвост списка в переменной oldTail.

        final Node<Task> newNode = new Node<Task>(oldTail, element, null);
        //- Создает новый узел (Node<Task>)
        // с текущим хвостом (oldTail),
        // данными задачи (element) и ссылкой на следующий узел (null, так как он будет новым хвостом).

        tail = newNode; // - Обновляет хвост (tail) на новый узел.
        receivedTasks.put(element.getId(), newNode); // - Добавляет новую задачу в хеш-таблицу receivedTasks, связывая её ID с новым узлом.

        if (oldTail == null) // - Проверяет, был ли список пустым до добавления новой задачи (то есть oldTail равно null).

            head = newNode; // - Если список был пуст, новая задача становится и головой списка.
        else
            oldTail.next = newNode; // - Если в списке уже есть элементы, устанавливает следующий узел у текущего хвоста на новый узел.
    }

    private List<Task> getTasks() { // - Объявляет метод getTasks, который собирает все задачи из двусвязного списка.

        List<Task> tasks = new ArrayList<>(); //Создает новый список tasks, который будет хранить задачи.

        Node<Task> currentNode = head; // - Устанавливает переменную currentNode на голову списка для начала обхода.

        while (!(currentNode == null)) { // - Запускает цикл, который будет продолжаться, пока есть текущий узел (то есть, пока не достигнут конец списка).

            tasks.add(currentNode.data);// - Добавляет данные текущего узла (задачу) в список tasks.

            currentNode = currentNode.next; // - Переходит к следующему узлу в списке.
        }
        return tasks; // - Возвращает список задач, собранный из двусвязного списка.
    }

    private void removeNode(Node<Task> node) { // - Объявляет метод removeNode, который удаляет данный узел из двусвязного списка.

        if (!(node == null)) { // - Проверяет, не равен ли узел null. Удаление не имеет смысла, если узел пуст.

            //- Сохраняет ссылки на следующий и предыдущий узлы текущего узла в переменные next и prev.
            final Node<Task> next = node.next;
            final Node<Task> prev = node.prev;

            node.data = null; // - Очищает данные узла, чтобы помочь сборщику мусора освободить память.

            if (head == node && tail == node) { // - Проверяет, является ли узел одновременно головой и хвостом списка (то есть единственным элементом).

                //Если это единственный узел, устанавливает голову и хвост на null.
                head = null;
                tail = null;

            } else if (head == node && !(tail == node)) { // - Проверяет, является ли узел головой, но не хвостом.

                // - Устанавливает новую голову на следующий узел и очищает его предыдущую ссылку.
                head = next;
                head.prev = null;

            } else if (!(head == node) && tail == node) { // - Проверяет, является ли узел хвостом, но не головой.

                // - Обновляет хвост на предыдущий узел и очищает его следующую ссылку.
                tail = prev;
                tail.next = null;

            } else { // - Это условие выполняется, если узел не был ни головой, ни хвостом и находится в середине списка.


                //- Обновляет ссылки на следующий и предыдущий узлы, чтобы исключить текущий узел из связи.
                prev.next = next;
                next.prev = prev;
            }

        }
    }

}
