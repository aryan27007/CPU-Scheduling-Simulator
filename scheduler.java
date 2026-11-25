import java.util.*;

class scheduler {

    static void calculate(List<Process> p, String algorithm, int quantum) {

        p.sort(Comparator.comparingInt(a -> a.arrival));

        List<ganttBlock> gantt = new ArrayList<>();

        if (algorithm.equals("FCFS")) {
            fcfs(p, gantt);
        } 
        else if (algorithm.equals("SJF")) {
            sjf(p, gantt);
        } 
        else if (algorithm.equals("PRIORITY")) {
            priority(p, gantt);
        } 
        else if (algorithm.equals("RR")) {
            roundRobin(p, gantt, quantum);
        }

        HashMap<String, Process> results = new HashMap<>();

        double totalWT = 0;
        double totalTAT = 0;

        for (Process x : p) {
            x.turnaround = x.completion - x.arrival;
            x.waiting = x.turnaround - x.burst;

            results.put(x.id, x);

            totalWT += x.waiting;
            totalTAT += x.turnaround;
        }

        System.out.println("\nGANTT CHART:");

        for (ganttBlock b : gantt) {
            System.out.print("| " + b.id + " " + b.start + "-" + b.end + " ");
        }

        System.out.println("|");

        System.out.println("\nID\tCT\tTAT\tWT");

        for (Process x : results.values()) {
            System.out.println(x.id + "\t" +
                x.completion + "\t" +
                x.turnaround + "\t" +
                x.waiting);
        }

        System.out.println("\nAverage Waiting Time: " +
            totalWT / p.size());

        System.out.println("Average Turnaround Time: " +
            totalTAT / p.size());
    }

    static void fcfs(List<Process> p, List<ganttBlock> g) {
        int time = 0;

        for (Process x : p) {
            time = Math.max(time, x.arrival);

            int start = time;
            time += x.burst;

            x.completion = time;
            g.add(new ganttBlock(x.id, start, time));
        }
    }

    static void sjf(List<Process> p, List<ganttBlock> g) {
        PriorityQueue<Process> q =
            new PriorityQueue<>(Comparator.comparingInt(x -> x.burst));

        int time = 0, i = 0, done = 0;

        while (done < p.size()) {

            while (i < p.size() && p.get(i).arrival <= time)
                q.add(p.get(i++));

            if (q.isEmpty()) {
                time = p.get(i).arrival;
                continue;
            }

            Process x = q.poll();

            int start = time;
            time += x.burst;

            x.completion = time;
            g.add(new ganttBlock(x.id, start, time));

            done++;
        }
    }

    static void priority(List<Process> p, List<ganttBlock> g) {
        PriorityQueue<Process> q =
            new PriorityQueue<>(Comparator.comparingInt(x -> x.priority));

        int time = 0, i = 0, done = 0;

        while (done < p.size()) {

            while (i < p.size() && p.get(i).arrival <= time)
                q.add(p.get(i++));

            if (q.isEmpty()) {
                time = p.get(i).arrival;
                continue;
            }

            Process x = q.poll();

            int start = time;
            time += x.burst;

            x.completion = time;
            g.add(new ganttBlock(x.id, start, time));

            done++;
        }
    }

    static void roundRobin(List<Process> p,
                           List<ganttBlock> g, int quantum) {

        Queue<Process> q = new LinkedList<>();

        int time = 0, i = 0, done = 0;

        while (done < p.size()) {

            while (i < p.size() && p.get(i).arrival <= time)
                q.add(p.get(i++));

            if (q.isEmpty()) {
                time = p.get(i).arrival;
                continue;
            }

            Process x = q.poll();

            int start = time;
            int run = Math.min(quantum, x.remaining);

            time += run;
            x.remaining -= run;

            g.add(new ganttBlock(x.id, start, time));

            while (i < p.size() && p.get(i).arrival <= time)
                q.add(p.get(i++));

            if (x.remaining > 0)
                q.add(x);
            else {
                x.completion = time;
                done++;
            }
        }
    }
}