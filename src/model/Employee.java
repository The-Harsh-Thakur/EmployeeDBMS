package model;

import java.io.Serializable;

/**
 * Employee class representing a generic employee record.
 * Implements Serializable for potential future file storage if needed.
 */
public class Employee implements Serializable {
    private int id;             // Unique employee ID
    private String name;        // Employee's name
    private double salary;      // Employee's salary
    private String department;  // Employee's department

    /**
     * Constructor for Employee.
     * Includes validation to ensure consistent and clean data.
     *
     * @param id         Employee ID (must be positive).
     * @param name       Employee name (cannot be empty).
     * @param salary     Employee salary (cannot be negative).
     * @param department Department name (cannot be empty).
     */
    public Employee(int id, String name, double salary, String department) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        if (department == null || department.isEmpty()) throw new IllegalArgumentException("Department cannot be empty.");

        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    // Getter methods for each field

    /**
     * @return Employee ID
     */
    public int getId() { return id; }

    /**
     * @return Employee name
     */
    public String getName() { return name; }

    /**
     * @return Employee salary
     */
    public double getSalary() { return salary; }

    /**
     * @return Employee department
     */
    public String getDepartment() { return department; }

    /**
     * Setter to update the employee's salary.
     *
     * @param salary New salary to set
     */
    public void setSalary(double salary) { this.salary = salary; }

    /**
     * Returns a clean string representation of the employee,
     * useful for displaying in console outputs.
     */
    @Override
    public String toString() {
        return "Employee [ID=" + id +
                ", Name=" + name +
                ", Salary=" + salary +
                ", Department=" + department + "]";
    }
}
