package xperience;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordListTest {

    private PasswordList passwordList;

    @BeforeEach
    public void setUp() throws Exception {
        passwordList = new PasswordList("src/test/resources/passwords.txt");
    }

@Test
public void testValidPasswords() {
    assertTrue(passwordList.validate("Danoke", "pass123"));
    passwordList.usePassword("Danoke"); // consume pass123

    assertTrue(passwordList.validate("Danoke", "pass456")); // now this one matches
}

    @Test
    public void testUsePassword() {
        assertTrue(passwordList.validate("Safety", "secure1"));
        passwordList.usePassword("Safety");
        assertFalse(passwordList.validate("Safety", "secure1")); // secure1 should be used now
        assertTrue(passwordList.validate("Safety", "secure2"));  // secure2 still available
    }

    @Test
    public void testReuseDifferentPasswordFails() {
        assertTrue(passwordList.validate("Dona_Dance", "groove1"));
        passwordList.usePassword("Dona_Dance");
        assertFalse(passwordList.validate("Dona_Dance", "groove1")); // groove1 used
        assertTrue(passwordList.validate("Dona_Dance", "groove2"));  // groove2 should still be valid
    }
}
