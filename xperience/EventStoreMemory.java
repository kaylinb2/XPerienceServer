package xperience;

import java.util.concurrent.ConcurrentHashMap;

public class EventStoreMemory implements EventStore {
    private final ConcurrentHashMap<String, Event> events = new ConcurrentHashMap<>();

    @Override
    public boolean add(Event event) {
        return events.putIfAbsent(event.name(), event) == null;
    }

    @Override
    public boolean contains(String name) {
        return events.containsKey(name);
    }

    @Override
    public int size() {
        return events.size();
    }
}
