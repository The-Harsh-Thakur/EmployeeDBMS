import model.Employee;
import util.EmployeeDao;

import java.util.Scanner;

/**
 * Main class for the Employee Management System.
 * Allows:
 * - Adding employees (with reportsTo)
 * - Displaying employees
 * - Removing employees
 * - Updating salary
 * - Making an employee a manager.
 */
public class Main {
    public static void main(String[] args) {
        EmployeeDao db = new EmployeeDao(); // DAO for DB operations
        Scanner sc = new Scanner(System.in);
        int choice; // User's menu choice

        do {
            // Display menu
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Remove Employee");
            System.out.println("4. Update Employee (Salary / Make Manager)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    // Add Employee with reportsTo
                    System.out.print("Employee ID (alphanumeric): ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine(); // consume newline
                    System.out.print("Department: ");
                    String department = sc.nextLine();
                    System.out.print("Enter Manager ID to whom this employee reports (or leave blank if none): ");
                    String reportsTo = sc.nextLine();
                    if (reportsTo.isBlank()) {
                        reportsTo = null; // Treat blank as null
                    }

                    db.addEmployee(new Employee(id, name, salary, department, reportsTo));
                }
                case 2 -> {
                    // Display all employees
                    db.displayAll();
                }
                case 3 -> {
                    // Remove employee by ID
                    System.out.print("Enter Employee ID to remove: ");
                    String id = sc.nextLine();
                    db.removeEmployee(id);
                }
                case 4 -> {
                    // Update section: allows making manager or updating salary
                    System.out.println("1. Update Salary");
                    System.out.println("2. Make Manager");
                    System.out.print("Enter your choice: ");
                    int updateChoice = sc.nextInt();
                    sc.nextLine(); // consume newline

                    if (updateChoice == 1) {
                        // Update salary
                        System.out.print("Enter Employee ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter New Salary: ");
                        double newSalary = sc.nextDouble();
                        sc.nextLine(); // consume newline

                        db.updateSalary(id, newSalary);
                    } else if (updateChoice == 2) {
                        // Make Manager
                        System.out.print("Enter Employee ID to promote to Manager: ");
                        String empId = sc.nextLine();
                        db.makeManager(empId);
                    } else {
                        System.out.println("Invalid choice for update.");
                    }
                }
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice, please try again.");
            }

        } while (choice != 5);

        sc.close(); // Free resources
    }
}
