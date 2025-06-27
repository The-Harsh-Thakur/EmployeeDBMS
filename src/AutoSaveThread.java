public class AutoSaveThread extends Thread {
    private final EmployeeDatabase db;

    public AutoSaveThread(EmployeeDatabase db) {
        this.db = db;
        setDaemon(true);
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10000); // Save every 10 seconds
                db.saveToFile();
                System.out.println("[AutoSave] Data saved.");
            } catch (InterruptedException e) {
                System.out.println("AutoSave interrupted.");
            }
        }
    }
}
