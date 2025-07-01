package util;

import model.Employee;
import java.sql.*;

/**
 * EmployeeDao handles all database operations:
 * - Add Employee (with reportsTo)
 * - Display all Employees
 * - Remove Employee
 * - Update Employee salary
 * - Promote Employee to Manager
 *
 * Uses MySQL via JDBC with environment-secured credentials.
 */
public class EmployeeDao {

    // Database connection details
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/employeedb";
    private final String JDBC_USER = System.getenv("DB_USER");        // Set in your environment
    private final String JDBC_PASS = System.getenv("DB_PASSWORD");

    /**
     * Adds a new employee to the employees table, with optional reportsTo.
     *
     * @param emp Employee object with details
     */
    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employee (id, name, salary, department, reports_to) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getId());
            stmt.setString(2, emp.getName());
            stmt.setDouble(3, emp.getSalary());
            stmt.setString(4, emp.getDepartment());

            if (emp.getReportsTo() != null && !emp.getReportsTo().isBlank()) {
                stmt.setString(5, emp.getReportsTo());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            stmt.executeUpdate();
            System.out.println("Employee added to the database.");
        } catch (SQLException e) {
            System.out.println("Database error while adding employee: " + e.getMessage());
        }
    }

    /**
     * Displays all employees, indicating if they are managers and whom they report to.
     */
    public void displayAll() {
        String sql = "SELECT e.id, e.name, e.salary, e.department, e.reports_to, " +
                "CASE WHEN m.id IS NOT NULL THEN TRUE ELSE FALSE END AS is_manager " +
                "FROM employee e LEFT JOIN managers m ON e.id = m.id";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- All Employees ---");
            boolean found = false;

            while (rs.next()) {
                found = true;
                String id = rs.getString("id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                String department = rs.getString("department");
                String reportsTo = rs.getString("reports_to");
                boolean isManager = rs.getBoolean("is_manager");

                System.out.printf("%s [ID=%s, Name=%s, Salary=%.2f, Department=%s",
                        isManager ? "Manager" : "Employee", id, name, salary, department);

                if (reportsTo != null) {
                    System.out.printf(", ReportsTo=%s", reportsTo);
                }
                System.out.println("]");
            }

            if (!found) {
                System.out.println("No employees found in the database.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while displaying employees: " + e.getMessage());
        }
    }

    /**
     * Removes an employee from the database.
     *
     * @param id Employee ID
     */
    public void removeEmployee(String id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee removed from the database.");
            } else {
                System.out.println("No employee with ID " + id + " found.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while removing employee: " + e.getMessage());
        }
    }

    /**
     * Updates the salary of an employee.
     *
     * @param id        Employee ID
     * @param newSalary New salary
     */
    public void updateSalary(String id, double newSalary) {
        String sql = "UPDATE employee SET salary = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newSalary);
            stmt.setString(2, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Salary updated for employee ID " + id + ".");
            } else {
                System.out.println("No employee with ID " + id + " found.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while updating salary: " + e.getMessage());
        }
    }

    /**
     * Promotes an existing employee to a manager by adding their ID to the managers table.
     *
     * @param empId Employee ID to promote
     */
    public void makeManager(String empId) {
        String checkEmployee = "SELECT id FROM employee WHERE id = ?";
        String insertManager = "INSERT INTO managers (id) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
             PreparedStatement checkStmt = conn.prepareStatement(checkEmployee)) {

            checkStmt.setString(1, empId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertManager)) {
                    insertStmt.setString(1, empId);
                    insertStmt.executeUpdate();

                    System.out.println("Employee with ID " + empId + " promoted to Manager.");
                }
            } else {
                System.out.println("No employee with ID " + empId + " found.");
            }
        } catch (SQLException e) {
            System.out.println("Database error while promoting to manager: " + e.getMessage());
        }
    }
}
