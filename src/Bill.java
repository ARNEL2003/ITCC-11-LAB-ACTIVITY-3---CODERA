package src;

import java.util.HashMap;
import java.util.Map;

public class Bill {
    private int billID;
    private int customerID;
    private Map<Integer, Integer> taskHours;
    private double totalCost;

    public Bill(int billID, int customerID) {
        this.billID = billID;
        this.customerID = customerID;
        this.taskHours = new HashMap<>();
        this.totalCost = 0.0;
    }

    public int getBillID() {
        return billID;
    }

    public void addTask(int taskID, int hoursWorked, double hourlyRate) {
        taskHours.put(taskID, taskHours.getOrDefault(taskID, 0) + hoursWorked);
        updateTotal(hoursWorked * hourlyRate);
    }

    private void updateTotal(double cost) {
        this.totalCost += cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Map<Integer, Integer> getTaskHours() {
        return taskHours;
    }
}
