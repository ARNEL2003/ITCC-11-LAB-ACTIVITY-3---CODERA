package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BillingSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            processChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Virtual Assistant Billing System ===");
        System.out.println("0. Exit");
        System.out.println("1. Generate New Bill");
        System.out.println("2. View Bills by Customer");
        System.out.println("3. View All Bills");
        System.out.println("4. Remove Bill");
        System.out.println("5. Register Customer");
        System.out.println("6. Show Customers");
        System.out.println("7. Modify Customer");
        System.out.println("8. Remove Customer");
        System.out.println("9. Add Task");
        System.out.println("10. List Tasks");
        System.out.println("11. Modify Task");
        System.out.println("12. Remove Task");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static void processChoice(int choice) {
        switch (choice) {
            case 0:
                exitSystem();
                break;
            case 1:
                generateBill();
                break;
            case 2:
                // Implement viewBillsByCustomer()
                break;
            case 3:
                // Implement showAllBills()
                break;
            case 4:
                // Implement removeBill()
                break;
            case 5:
                registerCustomer();
                break;
            case 6:
                listCustomers();
                break;
            case 7:
                modifyCustomer();
                break;
            case 8:
                removeCustomer();
                break;
            case 9:
                addTask();
                break;
            case 10:
                listTasks();
                break;
            case 11:
                modifyTask();
                break;
            case 12:
                removeTask();
                break;
            default:
                System.out.println("Invalid selection. Please try again.");
        }
    }

    private static void exitSystem() {
        System.out.println("Shutting down system...");
        scanner.close();
        System.exit(0);
    }

    private static void registerCustomer() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        executeUpdate("INSERT INTO customers (name) VALUES (?)", name);
        System.out.println("Customer successfully registered!");
    }

    private static void listCustomers() {
        executeQuery("SELECT * FROM customers", rs -> {
            System.out.println("\n=== Customers ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("customer_id") + " | Name: " + rs.getString("name"));
            }
        });
    }

    private static void modifyCustomer() {
        System.out.print("Enter customer ID to update: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new customer name: ");
        String newName = scanner.nextLine();
        executeUpdate("UPDATE customers SET name = ? WHERE customer_id = ?", newName, customerId);
        System.out.println("Customer details updated successfully!");
    }

    private static void removeCustomer() {
        System.out.print("Enter customer ID to delete: ");
        int customerId = scanner.nextInt();
        executeUpdate("DELETE FROM customers WHERE customer_id = ?", customerId);
        System.out.println("Customer removed successfully!");
    }

    private static void addTask() {
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter hourly rate: ");
        double rate = scanner.nextDouble();
        executeUpdate("INSERT INTO tasks (task_name, rate) VALUES (?, ?)", taskName, rate);
        System.out.println("Task added successfully!");
    }

    private static void listTasks() {
        executeQuery("SELECT * FROM tasks", rs -> {
            System.out.println("\n=== Available Tasks ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("task_id") + " | Name: " + rs.getString("task_name")
                        + " | Rate: Php" + rs.getDouble("rate") + "/hr");
            }
        });
    }

    private static void modifyTask() {
        System.out.print("Enter task ID to update: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new task name: ");
        String newTaskName = scanner.nextLine();
        System.out.print("Enter new rate: ");
        double newRate = scanner.nextDouble();
        executeUpdate("UPDATE tasks SET task_name = ?, rate = ? WHERE task_id = ?", newTaskName, newRate, taskId);
        System.out.println("Task updated successfully!");
    }

    private static void removeTask() {
        System.out.print("Enter task ID to delete: ");
        int taskId = scanner.nextInt();
        executeUpdate("DELETE FROM tasks WHERE task_id = ?", taskId);
        System.out.println("Task removed successfully!");
    }

    private static void executeUpdate(String sql, Object... params) {
        try (Connection conn = DBConnector.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database operation failed.");
        }
    }

    private static void executeQuery(String sql, QueryExecutor executor) {
        try (Connection conn = DBConnector.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            executor.process(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error executing query.");
        }
    }

    // ... (generateBill() method
    private static void generateBill() {
        try (Connection conn = DBConnector.connect()) {
            System.out.print("Enter customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();

            if (!exists(conn, "SELECT COUNT(*) FROM customers WHERE customer_id = ?", customerId)) {
                System.out.println("Customer with ID " + customerId + " does not exist.");
                return;
            }

            List<BillItem> billItems = new ArrayList<>();
            while (true) {
                System.out.print("Enter task ID (or 0 to finish): ");
                int taskId = scanner.nextInt();
                scanner.nextLine();
                if (taskId == 0)
                    break;

                if (!exists(conn, "SELECT COUNT(*) FROM tasks WHERE task_id = ?", taskId)) {
                    System.out.println("Task with ID " + taskId + " does not exist.");
                    continue;
                }

                System.out.print("Enter hours worked: ");
                int hoursWorked = scanner.nextInt();
                scanner.nextLine();

                double rate = getTaskRate(conn, taskId);
                billItems.add(new BillItem(taskId, hoursWorked, rate));
            }

            double totalCost = billItems.stream().mapToDouble(BillItem::calculateCost).sum();
            System.out.println("Total Cost: Php" + totalCost);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database operation failed.");
        }
    }

    private static boolean exists(Connection conn, String query, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private static double getTaskRate(Connection conn, int taskId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT rate FROM tasks WHERE task_id = ?")) {
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble("rate") : 0;
        }
    }

    @FunctionalInterface
    private interface QueryExecutor {
        void process(ResultSet rs) throws SQLException;
    }
}
