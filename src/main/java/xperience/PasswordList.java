package xperience;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PasswordList {
    private final Map<String, Queue<String>> passwordMap = new HashMap<>();

    public PasswordList(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            String currentUser = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // If line starts with uppercase, treat it as username
                if (Character.isUpperCase(line.charAt(0))) {
                    currentUser = line;
                    passwordMap.putIfAbsent(currentUser, new LinkedList<>());
                } else if (currentUser != null) {
                    passwordMap.get(currentUser).add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Password file not found: " + filename);
        }
    }

    public boolean validate(String user, String password) {
        Queue<String> queue = passwordMap.get(user);
        return queue != null && !queue.isEmpty() && queue.peek().equals(password);
    }

    public void usePassword(String user) {
        Queue<String> queue = passwordMap.get(user);
        if (queue != null && !queue.isEmpty()) {
            queue.poll(); // Remove the used password
        }
    }
}
