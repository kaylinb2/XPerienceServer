package xperience;

import java.io.*;
import java.util.*;

public class PasswordList {
    private Set<String> passwords;

    // 📂 Constructor: loads the password list from the password file
    public PasswordList(String passwordFilePath) throws IOException {
        passwords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(passwordFilePath))) {
            String password;
            while ((password = reader.readLine()) != null) {
                passwords.add(password);
            }
        }
    }

    // 🔑 Check if the password exists in the list
    public boolean contains(String password) {
        return passwords.contains(password);
    }

    // 🗑️ Remove the password after it is used
    public void remove(String password) {
        passwords.remove(password);
    }
}
