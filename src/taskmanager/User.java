package taskmanager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a normal user with a list of tasks.
 */
public class User {
    private String username;
    private ArrayList<Task> tasks;

    public User(String username) {
        this.username = username;
        this.tasks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("[" + (i + 1) + "]\n" + tasks.get(i));
            }
        }
    }

    public void deleteTask(Scanner sc) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to delete.");
            return;
        }

        listTasks();
        System.out.print("Enter the number of the task to delete: ");
        String input = sc.nextLine().trim();

        int index;
        try {
            index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        System.out.print("Are you sure you want to delete this task? (Y/N): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            tasks.remove(index);
            System.out.println("✅ Task deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public void editTask(Scanner sc) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to edit.");
            return;
        }

        listTasks();
        System.out.print("Enter the number of the task to edit: ");
        String indexInput = sc.nextLine().trim();

        int index;
        try {
            index = Integer.parseInt(indexInput) - 1;
            if (index < 0 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        Task task = tasks.get(index);

        System.out.print("New Title (press Enter to skip): ");
        String title = sc.nextLine();
        if (!title.isEmpty()) task.setTitle(title);

        System.out.print("New Description (press Enter to skip): ");
        String desc = sc.nextLine();
        if (!desc.isEmpty()) task.setDescription(desc);

        System.out.println("Select Priority (press Enter to skip):\n1. Low\n2. Medium\n3. High");
        String pInput = sc.nextLine().trim();
        String priority = switch (pInput) {
            case "1" -> "Low";
            case "2" -> "Medium";
            case "3" -> "High";
            default -> "";
        };
        if (!priority.isEmpty()) task.setPriority(priority);

        System.out.println("Select Status (press Enter to skip):\n1. Pending\n2. In Progress\n3. Completed");
        String sInput = sc.nextLine().trim();
        String status = switch (sInput) {
            case "1" -> "Pending";
            case "2" -> "In Progress";
            case "3" -> "Completed";
            default -> "";
        };
        if (!status.isEmpty()) task.setStatus(status);

        System.out.println("Select Tag (press Enter to skip):\n1. Work\n2. Fitness\n3. School\n4. House Chores\n5. Else");
        String tInput = sc.nextLine().trim();
        String tag = switch (tInput) {
            case "1" -> "Work";
            case "2" -> "Fitness";
            case "3" -> "School";
            case "4" -> "House Chores";
            case "5" -> "Else";
            default -> "";
        };
        if (!tag.isEmpty()) task.setTag(tag);

        System.out.println("Do you want to update the due date?\n1. Yes\n2. No");
        String updateDate = sc.nextLine().trim();
        if (updateDate.equals("1")) {
            int currentYear = java.time.Year.now().getValue();
            int year = -1, month = -1, day = -1;

            System.out.println("Select Year:");
            for (int i = 0; i < 5; i++) {
                System.out.println((i + 1) + ". " + (currentYear + i));
            }
            String yInput = sc.nextLine().trim();
            if (!yInput.isEmpty()) {
                try {
                    int yChoice = Integer.parseInt(yInput);
                    if (yChoice >= 1 && yChoice <= 5) year = currentYear + (yChoice - 1);
                } catch (NumberFormatException ignored) {}
            }

            System.out.println("Select Month (01-12) or press Enter to skip:");
            for (int i = 1; i <= 12; i++) System.out.println(String.format("%02d", i));
            String mInput = sc.nextLine().trim();
            if (!mInput.isEmpty()) {
                try {
                    int m = Integer.parseInt(mInput);
                    if (m >= 1 && m <= 12) month = m;
                } catch (NumberFormatException ignored) {}
            }

            System.out.println("Enter Day (1–31) or press Enter to skip:");
            String dInput = sc.nextLine().trim();
            if (!dInput.isEmpty()) {
                try {
                    int d = Integer.parseInt(dInput);
                    if (d >= 1 && d <= 31) day = d;
                } catch (NumberFormatException ignored) {}
            }

            if (year != -1 || month != -1 || day != -1) {
                String newDate = String.format("%04d-%02d-%02d",
                        (year == -1 ? 0 : year),
                        (month == -1 ? 0 : month),
                        (day == -1 ? 0 : day));
                task.setDueDate(newDate);
            }
        }

        System.out.println("✅ Task updated!");
    }

    public void searchTasks(Scanner sc) {
        while (true) {
            System.out.println("Search Options:");
            System.out.println("1. Keyword in title/description");
            System.out.println("2. Filter by Priority");
            System.out.println("3. Filter by Status");
            System.out.println("4. Filter by Tag");
            System.out.println("5. Filter by Due Date (any part)");
            System.out.println("6. Exit to Main Menu");
            System.out.print("Enter choice: ");
            String inputStr = sc.nextLine().trim();
            if (inputStr.isEmpty()) {
                System.out.println("Invalid input. Please select a valid option.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            if (choice == 6) return;

            ArrayList<Integer> matchedIndices = new ArrayList<>();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter keyword: ");
                    String keyword = sc.nextLine().toLowerCase();
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        if (task.getTitle().toLowerCase().contains(keyword) ||
                                task.getDescription().toLowerCase().contains(keyword)) {
                            matchedIndices.add(i);
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Select Priority:\n1. Low\n2. Medium\n3. High");
                    String input = sc.nextLine().trim();
                    String p = switch (input) {
                        case "1" -> "Low";
                        case "2" -> "Medium";
                        case "3" -> "High";
                        default -> "";
                    };
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).getPriority().equalsIgnoreCase(p)) {
                            matchedIndices.add(i);
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Select Status:\n1. Pending\n2. In Progress\n3. Completed");
                    String input = sc.nextLine().trim();
                    String s = switch (input) {
                        case "1" -> "Pending";
                        case "2" -> "In Progress";
                        case "3" -> "Completed";
                        default -> "";
                    };
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).getStatus().equalsIgnoreCase(s)) {
                            matchedIndices.add(i);
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Select Tag:\n1. Work\n2. Fitness\n3. School\n4. House Chores\n5. Else");
                    String input = sc.nextLine().trim();
                    String t = switch (input) {
                        case "1" -> "Work";
                        case "2" -> "Fitness";
                        case "3" -> "School";
                        case "4" -> "House Chores";
                        case "5" -> "Else";
                        default -> "";
                    };
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).getTag().equalsIgnoreCase(t)) {
                            matchedIndices.add(i);
                        }
                    }
                }
                case 5 -> {
                    String year = "", month = "", day = "";
                    int currentYear = java.time.Year.now().getValue();

                    System.out.println("Select Year (or press Enter to skip):");
                    for (int i = 0; i < 5; i++) {
                        System.out.println((i + 1) + ". " + (currentYear + i));
                    }
                    String yInput = sc.nextLine().trim();
                    if (!yInput.isEmpty()) {
                        try {
                            int yChoice = Integer.parseInt(yInput);
                            if (yChoice >= 1 && yChoice <= 5) {
                                year = String.valueOf(currentYear + (yChoice - 1));
                            }
                        } catch (NumberFormatException ignored) {}
                    }

                    System.out.println("Select Month (01-12) or press Enter to skip:");
                    for (int i = 1; i <= 12; i++) {
                        System.out.println(String.format("%02d", i));
                    }
                    String mInput = sc.nextLine().trim();
                    if (!mInput.isEmpty()) {
                        try {
                            int m = Integer.parseInt(mInput);
                            if (m >= 1 && m <= 12) {
                                month = String.format("%02d", m);
                            }
                        } catch (NumberFormatException ignored) {}
                    }

                    System.out.println("Enter Day (1–31) or press Enter to skip:");
                    String dInput = sc.nextLine().trim();
                    if (!dInput.isEmpty()) {
                        try {
                            int d = Integer.parseInt(dInput);
                            if (d >= 1 && d <= 31) {
                                day = String.format("%02d", d);
                            }
                        } catch (NumberFormatException ignored) {}
                    }

                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        String due = task.getDueDate();
                        boolean match = true;
                        if (!year.isEmpty() && !due.startsWith(year)) match = false;
                        if (!month.isEmpty() && (due.length() < 7 || !due.substring(5, 7).equals(month))) match = false;
                        if (!day.isEmpty() && (due.length() < 10 || !due.substring(8).equals(day))) match = false;
                        if (match) matchedIndices.add(i);
                    }
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }

            System.out.println("\nSearch Results:");
            if (matchedIndices.isEmpty()) {
                System.out.println("No matching tasks found.");
            } else {
                for (int index : matchedIndices) {
                    System.out.println("[" + (index + 1) + "]\n" + tasks.get(index) + "\n");
                }
            }
            break;
        }
    }
}
