package xperience;

import java.util.List;

public interface EventStore {
    void addEvent(Event event);
    Event getEvent(String name);
    List<Event> getAllEvents();
}
