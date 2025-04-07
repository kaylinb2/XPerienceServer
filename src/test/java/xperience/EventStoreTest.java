package xperience;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventStoreTest {

    private EventStore store;

    @BeforeEach
    public void setUp() {
        store = new EventStoreMemory(); // or EventStoreDB if you're testing DB
    }

    @Test
    public void testAddAndContains() {
        Event event = new Event("Test Event", "2025-04-04", "12:00", "This is a test event");
        store.add(event); // assuming add returns void

        assertTrue(store.contains("Test Event"), "Event should be found in the store");
    }

    @Test
    public void testContainsFalse() {
        assertFalse(store.contains("Nonexistent Event"), "Should return false for event not in store");
    }
}
