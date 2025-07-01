package model;

/**
 * Manager class extending Employee.
 * Used to differentiate managers in logic and database operations.
 * Managers themselves may report to higher managers (reportsTo field).
 */
public class Manager extends Employee {

    /**
     * Constructor for Manager.
     * Calls the Employee constructor with the same fields, including reportsTo.
     *
     * @param id         Alphanumeric Employee ID (non-null, non-empty)
     * @param name       Employee name (non-null, non-empty)
     * @param salary     Employee salary (>= 0)
     * @param department Department name (non-null, non-empty)
     * @param reportsTo  Manager ID this manager reports to (nullable)
     */
    public Manager(String id, String name, double salary, String department, String reportsTo) {
        super(id, name, salary, department, reportsTo);
    }
}
