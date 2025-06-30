import model.Employee;
import model.Manager;
import util.EmployeeDatabase;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of EmployeeDatabase to handle all DB operations
        EmployeeDatabase db = new EmployeeDatabase();

        // Create a Scanner for user input
        Scanner sc = new Scanner(System.in);
        int choice; // variable to store user choice for the menu

        do {
            // Display menu options
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Manager");
            System.out.println("3. Display All Employees");
            System.out.println("4. Remove Employee");
            System.out.println("5. Update Salary");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt(); // read user choice

            // Handle user choice using switch-case
            switch (choice) {
                case 1 -> {
                    // Add Employee
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume leftover newline
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine(); // consume leftover newline
                    System.out.print("Department: ");
                    String department = sc.nextLine();
                    // Add a new Employee (non-manager) to the database
                    db.addEmployee(new Employee(id, name, salary, department));
                }
                case 2 -> {
                    // Add Manager
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume leftover newline
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine(); // consume leftover newline
                    System.out.print("Department: ");
                    String department = sc.nextLine();
                    // Add a new Manager to the database
                    db.addEmployee(new Manager(id, name, salary, department));
                }
                case 3 -> {
                    // Display all employees from the database
                    db.displayAll();
                }
                case 4 -> {
                    // Remove an employee by ID
                    System.out.print("Enter ID to remove: ");
                    int id = sc.nextInt();
                    db.removeEmployee(id);
                }
                case 5 -> {
                    // Update an employee's salary
                    System.out.print("Enter ID to update salary: ");
                    int id = sc.nextInt();
                    System.out.print("New Salary: ");
                    double newSalary = sc.nextDouble();
                    db.updateSalary(id, newSalary);
                }
                case 6 -> {
                    // Exit the program
                    System.out.println("Exiting...");
                }
                default -> {
                    // Handle invalid input
                    System.out.println("Invalid choice. Please try again.");
                }
            }

        } while (choice != 6); // continue until user chooses to exit

        // Close the scanner to free resources
        sc.close();
    }
}
