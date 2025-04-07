package xperience;

import java.util.ArrayList;
import java.util.List;

/**
 * 🧠 EventStoreMemory - Stores events in memory using a list
 */
public class EventStoreMemory implements EventStore {
    private final List<Event> events = new ArrayList<>();

    @Override
    public void add(Event event) {
        events.add(event);
    }

    @Override
    public boolean contains(String name) {
        return events.stream().anyMatch(e -> e.getName().equals(name));
    }

    @Override
    public List<Event> all() {
        return new ArrayList<>(events); // Return a copy to avoid direct modification
    }

    @Override
    public int size() {
        return events.size(); // ✅ Add this for event ID tracking
    }
}
