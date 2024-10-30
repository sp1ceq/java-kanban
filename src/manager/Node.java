package manager;

import tasks.Task;

class Node<T> { // отдельный класс Node для узла списка

    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Node<Task> prev, Task data, Node<Task> next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public Task getData() {
        return data;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}
// 1. Объявление класса и параметра типа:
//- class Node<T>: Класс определен как дженерик (универсальный) с параметром типа T.
// Это позволяет создавать узлы, которые могут хранить данные любого типа (хотя в вашей реализации вы используете его только для Task).
//
//2. Поля класса:
//- public Task data: Хранит данные узла типа Task. Это значение, которое будет храниться в этом узле.
//- public Node<Task> next: Указатель на следующий узел в двусвязном списке. Позволяет перемещаться по списку вперед.
//- public Node<Task> prev: Указатель на предыдущий узел в двусвязном списке. Позволяет перемещаться по списку назад.
//
//3. Конструктор:
//- public Node(Node<Task> prev, Task data, Node<Task> next): Конструктор класса, который инициализирует поля узла.
//- this.data = data;: Присваивает переданные данные в поле узла.
//- this.next = next;: Устанавливает указатель на следующий узел.
//- this.prev = prev;: Устанавливает указатель на предыдущий узел.