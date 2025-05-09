package taskmanager;

import java.io.*;
import java.util.ArrayList;

/**
 * Utility class to save and load user data.
 */
public class FileStorage {
    private static final String FILE_NAME = "data.ser";

    public static void saveUsers(ArrayList<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            System.out.println("✅ Data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("❌ Failed to save data: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                return (ArrayList<User>) obj;
            } else {
                System.out.println("❌ Data format invalid.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No existing data file. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Failed to load data: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}

