package util;

import model.Employee;
import model.Manager;

import java.sql.*;

/**
 * EmployeeDatabase handles all database operations related to employees:
 * - Adding new employees/managers
 * - Displaying all records
 * - Removing employees by ID
 * - Updating employee salaries
 *
 * Uses MySQL via JDBC.
 */
public class EmployeeDatabase {

    // Database connection details
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/employeedb";
    private final String JDBC_USER = System.getenv("DB_USER");        // fetched from environment variable for safety
    private final String JDBC_PASS = System.getenv("DB_PASSWORD");    // fetched from environment variable for safety

    /**
     * Adds an employee or manager to the database.
     * Uses a prepared statement to safely insert data.
     *
     * @param emp The Employee or Manager object to add.
     */
    public void addEmployee(Employee emp) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO employees (id, name, salary, department, isManager) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, emp.getId());
                stmt.setString(2, emp.getName());
                stmt.setDouble(3, emp.getSalary());
                stmt.setString(4, emp.getDepartment());
                stmt.setBoolean(5, emp instanceof Manager);  // true if Manager, false if Employee
                stmt.executeUpdate();
                System.out.println("Employee added to database.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while adding employee: " + e.getMessage());
        }
    }

    /**
     * Displays all employees and managers currently stored in the database.
     * Prints each record clearly to the console.
     */
    public void displayAll() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            System.out.println("\n--- All Employees ---");
            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                boolean isManager = rs.getBoolean("isManager");

                String type = isManager ? "Manager" : "Employee";
                System.out.printf("%s [ID=%d, Name=%s, Salary=%.2f, Department=%s]%n",
                        type, id, name, salary, department);
            }
            if (!found) {
                System.out.println("No employees found in database.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while displaying employees: " + e.getMessage());
        }
    }

    /**
     * Removes an employee (or manager) from the database using their ID.
     *
     * @param id ID of the employee to remove.
     */
    public void removeEmployee(int id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Employee removed from database.");
                } else {
                    System.out.println("No employee with ID " + id + " found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error while removing employee: " + e.getMessage());
        }
    }

    /**
     * Updates the salary of an employee with the given ID.
     *
     * @param id        ID of the employee whose salary should be updated.
     * @param newSalary The new salary to set.
     */
    public void updateSalary(int id, double newSalary) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "UPDATE employees SET salary = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, newSalary);
                stmt.setInt(2, id);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Salary updated for employee ID " + id + " in database.");
                } else {
                    System.out.println("No employee with ID " + id + " found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error while updating salary: " + e.getMessage());
        }
    }
}
