package src;

public class BillItem {
    private int taskId;
    private int hoursWorked;
    private double rate;

    public BillItem(int taskId, int hoursWorked, double rate) {
        this.taskId = taskId;
        this.hoursWorked = hoursWorked;
        this.rate = rate;
    }

    public double calculateCost() {
        return hoursWorked * rate;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getRate() {
        return rate;
    }
}
