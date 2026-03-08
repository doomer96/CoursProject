import java.util.*;

public class Main {

    static ArrayDeque<String> line = new ArrayDeque<>();
    static HashMap<String, Integer> arrivalTime = new HashMap<>();
    static int currentTime = 0;
    static long totalWait = 0;
    static int servedCount = 0;

    public static void printHelp() {
        System.out.println("Cafeteria Line Manager — Commands:");
        System.out.println("HELP");
        System.out.println("ARRIVE <name>");
        System.out.println("VIP_ARRIVE <name>");
        System.out.println("SERVE");
        System.out.println("LEAVE <name>");
        System.out.println("PEEK");
        System.out.println("SIZE");
        System.out.println("PRINT");
        System.out.println("TICK <minutes>");
        System.out.println("STATS");
        System.out.println("EXIT");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Type HELP to see commands.");

        while (true) {

            System.out.print("> ");
            String input = sc.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0].toUpperCase();

            switch (command) {

                case "HELP":
                    printHelp();
                    break;

                case "ARRIVE":
                    if (parts.length != 2) {
                        System.out.println("Invalid command.");
                        break;
                    }
                    String name = parts[1];
                    if (arrivalTime.containsKey(name)) {
                        System.out.println("Name already in system");
                        break;
                    }
                    line.addLast(name);
                    arrivalTime.put(name, currentTime);
                    System.out.println(name + " arrived at time " +
                            currentTime + ". Line size = " + line.size());
                    break;

                case "VIP_ARRIVE":
                    if (parts.length != 2) {
                        System.out.println("Invalid command.");
                        break;
                    }
                    name = parts[1];
                    if (arrivalTime.containsKey(name)) {
                        System.out.println("Name already in system");
                        break;
                    }
                    line.addFirst(name);
                    arrivalTime.put(name, currentTime);
                    System.out.println("VIP " + name + " arrived at time "
                            + currentTime + " (front). Line size = " + line.size());
                    break;

                case "SERVE":
                    if (line.isEmpty()) {
                        System.out.println("No one to serve.");
                        break;
                    }
                    String served = line.removeFirst();
                    int wait = currentTime - arrivalTime.get(served);
                    totalWait += wait;
                    servedCount++;
                    arrivalTime.remove(served);
                    System.out.println("Served: " + served +
                            " (waited " + wait + " min).");
                    break;

                case "LEAVE":
                    if (parts.length != 2) {
                        System.out.println("Invalid command.");
                        break;
                    }
                    name = parts[1];
                    boolean removed = line.removeFirstOccurrence(name);
                    if (!removed) {
                        System.out.println("Not found");
                    } else {
                        arrivalTime.remove(name);
                        System.out.println(name +
                                " left the line voluntarily. Line size = "
                                + line.size());
                    }
                    break;

                case "PEEK":
                    if (line.isEmpty()) {
                        System.out.println("Line is empty.");
                    } else {
                        System.out.println("Next: " + line.peekFirst());
                    }
                    break;

                case "SIZE":
                    System.out.println("Size: " + line.size());
                    break;

                case "PRINT":
                    System.out.println("Line (front -> back): " + line);
                    break;

                case "TICK":
                    if (parts.length != 2) {
                        System.out.println("Invalid command.");
                        break;
                    }
                    int minutes = Integer.parseInt(parts[1]);
                    if (minutes < 0) {
                        System.out.println("Minutes must be >= 0.");
                        break;
                    }
                    currentTime += minutes;
                    System.out.println("Time advanced by " + minutes +
                            " minutes. Current time = " + currentTime);
                    break;

                case "STATS":
                    double avg = 0;
                    if (servedCount > 0) {
                        avg = (double) totalWait / servedCount;
                    }
                    System.out.println("Served count = " + servedCount +
                            ", Avg wait = " + avg + " min.");
                    break;

                case "EXIT":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}