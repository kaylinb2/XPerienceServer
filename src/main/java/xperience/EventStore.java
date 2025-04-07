package xperience;

import java.util.List;

/**
 * 📦 Interface for storing events (in memory or DB).
 */
public interface EventStore {
    void add(Event event); // 🛠️ Add event to store
    boolean contains(String name); // 🔍 Check for duplicates
    List<Event> all(); // 📄 Get all events
    int size(); // 🧮 Count of events (✅ add this line)
}
