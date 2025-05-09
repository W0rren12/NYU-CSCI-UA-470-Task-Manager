package taskmanager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main application class that handles user interaction.
 */
public class TaskManagerApp {
    private static ArrayList<User> users = FileStorage.loadUsers();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Task Manager");
        System.out.println("Select Role:");
        System.out.println("1. Normal User");
        System.out.println("2. Admin");

        int role;
        while (true) {
            String roleInput = sc.nextLine().trim();
            if (roleInput.isEmpty()) {
                System.out.println("Invalid input. Please enter 1 or 2:");
                continue;
            }
            try {
                role = Integer.parseInt(roleInput);
                if (role != 1 && role != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2:");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }

        if (role == 1) {
            System.out.print("Enter your username: ");
            String username = sc.nextLine();

            User currentUser = null;
            for (User u : users) {
                if (u.getUsername().equalsIgnoreCase(username)) {
                    currentUser = u;
                    break;
                }
            }
            if (currentUser == null) {
                currentUser = new User(username);
                users.add(currentUser);
            }

            while (true) {
                System.out.println("\nUser Menu:");
                System.out.println("1. Create Task");
                System.out.println("2. View Tasks");
                System.out.println("3. Search Tasks");
                System.out.println("4. Edit Task");
                System.out.println("5. Delete Task");
                System.out.println("6. Exit");

                int choice;
                while (true) {
                    System.out.print("Enter choice: ");
                    String input = sc.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("Invalid input. Please enter a number.");
                        continue;
                    }
                    try {
                        choice = Integer.parseInt(input);
                        if (choice < 1 || choice > 6) {
                            System.out.println("Invalid choice. Please enter 1 to 6.");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                if (choice == 1) {
                    // --- Task creation logic (unchanged) ---
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Description: ");
                    String description = sc.nextLine();

                    System.out.println("Select Priority (press Enter to skip):\n1. Low\n2. Medium\n3. High");
                    String pInput = sc.nextLine().trim();
                    String priority = switch (pInput) {
                        case "1" -> "Low";
                        case "2" -> "Medium";
                        case "3" -> "High";
                        default -> "";
                    };

                    System.out.println("Select Status (press Enter to skip):\n1. Pending\n2. In Progress\n3. Completed");
                    String sInput = sc.nextLine().trim();
                    String status = switch (sInput) {
                        case "1" -> "Pending";
                        case "2" -> "In Progress";
                        case "3" -> "Completed";
                        default -> "";
                    };

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

                    System.out.println("Do you want to set a due date?\n1. Yes\n2. No");
                    String dateChoiceStr = sc.nextLine().trim();
                    String dueDate = "";

                    if (dateChoiceStr.equals("1")) {
                        int currentYear = java.time.Year.now().getValue();
                        int year = -1;
                        while (true) {
                            System.out.println("Select Year:");
                            for (int i = 0; i < 5; i++) {
                                System.out.println((i + 1) + ". " + (currentYear + i));
                            }
                            String yInput = sc.nextLine().trim();
                            if (yInput.isEmpty()) break;
                            try {
                                int yChoice = Integer.parseInt(yInput);
                                if (yChoice >= 1 && yChoice <= 5) {
                                    year = currentYear + (yChoice - 1);
                                    break;
                                }
                            } catch (NumberFormatException ignored) {}
                        }

                        int month = -1;
                        while (true) {
                            System.out.println("Select Month (01-12) or press Enter to skip:");
                            for (int i = 1; i <= 12; i++) {
                                System.out.println(String.format("%02d", i));
                            }
                            String mInput = sc.nextLine().trim();
                            if (mInput.isEmpty()) break;
                            try {
                                int m = Integer.parseInt(mInput);
                                if (m >= 1 && m <= 12) {
                                    month = m;
                                    break;
                                }
                            } catch (NumberFormatException ignored) {}
                        }

                        int day = -1;
                        while (true) {
                            System.out.println("Enter Day (1–31) or press Enter to skip:");
                            String dInput = sc.nextLine().trim();
                            if (dInput.isEmpty()) break;
                            try {
                                int d = Integer.parseInt(dInput);
                                if (d >= 1 && d <= 31) {
                                    day = d;
                                    break;
                                }
                            } catch (NumberFormatException ignored) {}
                        }

                        if (year != -1 || month != -1 || day != -1) {
                            dueDate = String.format("%04d-%02d-%02d",
                                    (year == -1 ? 0 : year),
                                    (month == -1 ? 0 : month),
                                    (day == -1 ? 0 : day));
                        }
                    }

                    Task task = new Task(title, description, dueDate, priority, status, tag);
                    currentUser.addTask(task);
                    System.out.println("✅ Task added!");

                } else if (choice == 2) {
                    currentUser.listTasks();
                } else if (choice == 3) {
                    currentUser.searchTasks(sc);
                } else if (choice == 4) {
                    currentUser.editTask(sc);
                } else if (choice == 5) {
                    currentUser.deleteTask(sc);
                } else if (choice == 6) {
                    FileStorage.saveUsers(users);
                    System.out.println("Goodbye!");
                    break;
                }
            }
        } else if (role == 2) {
            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. View All Users");
                System.out.println("2. Delete a User");
                System.out.println("3. Delete a User's Task");
                System.out.println("4. Reset System");
                System.out.println("5. Exit");

                System.out.print("Enter choice: ");
                String input = sc.nextLine().trim();
                int choice = -1;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                if (choice == 1) {
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        System.out.println("Registered Users:");
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println((i + 1) + ". " + users.get(i).getUsername());
                        }
                    }
                } else if (choice == 2) {
                    System.out.print("Enter username to delete: ");
                    String target = sc.nextLine().trim();
                    boolean removed = users.removeIf(u -> u.getUsername().equalsIgnoreCase(target));
                    if (removed) {
                        System.out.println("✅ User '" + target + "' deleted.");
                    } else {
                        System.out.println("User not found.");
                    }
                } else if (choice == 3) {
                    System.out.print("Enter username to access tasks: ");
                    String target = sc.nextLine().trim();
                    User found = null;
                    for (User u : users) {
                        if (u.getUsername().equalsIgnoreCase(target)) {
                            found = u;
                            break;
                        }
                    }
                    if (found == null) {
                        System.out.println("User not found.");
                        continue;
                    }
                    found.listTasks();
                    System.out.print("Enter task number to delete: ");
                    String numStr = sc.nextLine().trim();
                    try {
                        int num = Integer.parseInt(numStr);
                        if (num >= 1 && num <= found.getTasks().size()) {
                            found.getTasks().remove(num - 1);
                            System.out.println("✅ Task deleted.");
                        } else {
                            System.out.println("Invalid task number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                } else if (choice == 4) {
                    System.out.print("Are you sure you want to reset the system? (Y/N): ");
                    String confirm = sc.nextLine().trim();
                    if (confirm.equalsIgnoreCase("Y")) {
                        users.clear();
                        System.out.println("✅ System reset.");
                    } else {
                        System.out.println("Cancelled.");
                    }
                } else if (choice == 5) {
                    FileStorage.saveUsers(users);
                    System.out.println("Exiting Admin Menu.");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
