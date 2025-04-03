package xperience;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventStoreTest {

    @Test
    void testAddEvent() {
        EventStore store = new EventStoreMemory(); // Use in-memory event store for testing
        Event event = new Event("Danooke", "2025-02-12", "20:00", "Fusion of Karaoke and Dance");

        assertTrue(store.add(event)); // ✅ Should be true as the event is added
    }

    @Test
    void testEventExists() {
        EventStore store = new EventStoreMemory();
        Event event = new Event("Danooke", "2025-02-12", "20:00", "Fusion of Karaoke and Dance");
        store.add(event);

        assertTrue(store.contains("Danooke")); // 🔍 The event should be found
    }
}
