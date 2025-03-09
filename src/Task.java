package src;

public class Task {
    private int id;
    private String name;
    private double hourlyRate;

    public Task(String name, double hourlyRate) {
        this(-1, name, hourlyRate);
    }

    public Task(int id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public String toString() {
        return String.format("Task[ID: %d, Name: %s, Rate: Php%.2f/hr]", id, name, hourlyRate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}