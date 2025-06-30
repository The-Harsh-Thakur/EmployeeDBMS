package model;

/**
 * Manager class extending Employee.
 * Inherits all fields: id, name, salary, department.
 * Used to differentiate managers for the `isManager` field in the database.
 */
public class Manager extends Employee {

    /**
     * Constructor for Manager.
     *
     * @param id         Employee ID (must be positive).
     * @param name       Employee name (cannot be empty).
     * @param salary     Employee salary (cannot be negative).
     * @param department Department name (cannot be empty).
     */
    public Manager(int id, String name, double salary, String department) {
        // Calls the Employee constructor to initialize inherited fields
        super(id, name, salary, department);
    }
}
