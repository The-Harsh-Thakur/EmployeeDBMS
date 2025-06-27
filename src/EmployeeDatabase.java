import java.io.*;
import java.sql.*;
import java.util.*;

public class EmployeeDatabase {
    private List<Employee> employees = new ArrayList<>();
    private final String fileName = "employees.ser";

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private final String JDBC_USER = "your_username";
    private final String JDBC_PASS = "your_password";

    public void addEmployee(Employee emp) {
        employees.add(emp);
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO employees (id, name, salary, department) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, emp.getId());
                stmt.setString(2, emp.getName());
                stmt.setDouble(3, emp.getSalary());
                stmt.setString(4, emp instanceof Manager ? ((Manager) emp).toString() : null);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void displayAll() {
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    public void removeEmployee(int id) {
        employees.removeIf(e -> e.getId() == id);
    }

    public void updateSalary(int id, double newSalary) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                e.setSalary(newSalary);
                break;
            }
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            employees = (List<Employee>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}
