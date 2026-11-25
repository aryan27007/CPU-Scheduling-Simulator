import java.util.*;

public class main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Process> processes = new ArrayList<>();

        System.out.println("CPU SCHEDULING SIMULATOR");

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nProcess P" + i);

            System.out.print("Arrival Time: ");
            int arrival = sc.nextInt();

            System.out.print("Burst Time: ");
            int burst = sc.nextInt();

            System.out.print("Priority: ");
            int priority = sc.nextInt();

            processes.add(new Process(
                "P" + i, arrival, burst, priority));
        }

        System.out.println("\nSelect Algorithm:");
        System.out.println("1. FCFS");
        System.out.println("2. SJF");
        System.out.println("3. Priority");
        System.out.println("4. Round Robin");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        String algorithm = "";
        int quantum = 0;

        switch (choice) {
            case 1:
                algorithm = "FCFS";
                break;

            case 2:
                algorithm = "SJF";
                break;

            case 3:
                algorithm = "PRIORITY";
                break;

            case 4:
                algorithm = "RR";
                System.out.print("Enter Time Quantum: ");
                quantum = sc.nextInt();
                break;

            default:
                System.out.println("Invalid choice!");
                sc.close();
                return;
        }

        scheduler.calculate(processes, algorithm, quantum);

        sc.close();
    }
}
