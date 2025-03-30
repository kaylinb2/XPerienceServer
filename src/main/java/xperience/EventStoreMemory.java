package xperience;

import java.util.*;

public class EventStoreMemory implements EventStore {
    private final Map<String, Event> events = new HashMap<>();

    @Override
    public void addEvent(Event event) {
        events.put(event.getName(), event);
    }

    @Override
    public Event getEvent(String name) {
        return events.get(name);
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }
}
