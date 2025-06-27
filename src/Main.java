import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeDatabase db = new EmployeeDatabase();
        db.loadFromFile();

        AutoSaveThread autoSave = new AutoSaveThread(db);
        autoSave.start();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Manager");
            System.out.println("3. Display All");
            System.out.println("4. Remove Employee");
            System.out.println("5. Update Salary");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("ID: "); int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Salary: "); double salary = sc.nextDouble();
                    db.addEmployee(new Employee(id, name, salary));
                }
                case 2 -> {
                    System.out.print("ID: "); int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Salary: "); double salary = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Department: "); String dept = sc.nextLine();
                    db.addEmployee(new Manager(id, name, salary, dept));
                }
                case 3 -> db.displayAll();
                case 4 -> {
                    System.out.print("Enter ID to remove: ");
                    int id = sc.nextInt();
                    db.removeEmployee(id);
                }
                case 5 -> {
                    System.out.print("Enter ID to update: ");
                    int id = sc.nextInt();
                    System.out.print("New Salary: ");
                    double salary = sc.nextDouble();
                    db.updateSalary(id, salary);
                }
                case 6 -> {
                    db.saveToFile();
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 6);

        sc.close();
    }
}
