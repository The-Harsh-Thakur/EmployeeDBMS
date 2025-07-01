package model;
/**
 * Employee class representing a generic employee.
 * Implements Serializable for potential file-based serialization if needed in the future.
 */
public class Employee {

    private String id;           // Alphanumeric unique employee ID
    private String name;         // Employee's name
    private double salary;       // Employee's salary
    private String department;   // Employee's department
    private String reportsTo;    // ID of the manager this employee reports to (nullable)

    /**
     * Constructor for Employee.
     *
     * @param id         Alphanumeric Employee ID (non-null, non-empty)
     * @param name       Employee name (non-null, non-empty)
     * @param salary     Employee salary (>= 0)
     * @param department Department name (non-null, non-empty)
     * @param reportsTo  Manager ID this employee reports to (nullable, can be null or empty)
     */
    public Employee(String id, String name, double salary, String department, String reportsTo) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("ID cannot be empty.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        if (department == null || department.isEmpty()) throw new IllegalArgumentException("Department cannot be empty.");

        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.reportsTo = (reportsTo != null && !reportsTo.isBlank()) ? reportsTo : null;
    }

    /**
     * @return Employee ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return Employee name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Employee salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @return Employee department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @return Manager ID this employee reports to, or null if none
     */
    public String getReportsTo() {
        return reportsTo;
    }

    /**
     * Updates the employee's salary.
     *
     * @param salary new salary to set (>= 0)
     */
    public void setSalary(double salary) {
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        this.salary = salary;
    }

    /**
     * Sets or updates the reportsTo field.
     *
     * @param reportsTo Manager ID (nullable)
     */
    public void setReportsTo(String reportsTo) {
        this.reportsTo = (reportsTo != null && !reportsTo.isBlank()) ? reportsTo : null;
    }
}
