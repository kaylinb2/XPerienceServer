package xperience;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordListTest {

    @Test
    void testPasswordExists() {
        // 📖 Assuming the password file is simple and exists in the test directory
        PasswordList passwordList = new PasswordList("passwords.txt");

        assertTrue(passwordList.contains("correctPassword")); // ✅ "correctPassword" should be in the file
    }

    @Test
    void testRemovePassword() {
        PasswordList passwordList = new PasswordList("passwords.txt");
        String password = "correctPassword";

        assertTrue(passwordList.contains(password)); // ✅ It should initially exist
        passwordList.remove(password);
        assertFalse(passwordList.contains(password)); // 🗑️ After removal, it should no longer exist
    }
}
