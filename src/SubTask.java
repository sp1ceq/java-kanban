public class SubTask extends Task {

    private final int epicID;

    public SubTask(String text, String description, int epicID) {
        super(text, description);
        this.epicID = epicID;
    }

    public SubTask(int id, String text, String description, TaskStatus status, int epicID) {
        super(id, text, description, status);
        this.epicID = epicID;
    }

     int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", epicID=" + epicID +
                ", status=" + getStatus() +
                '}';
    }


}

