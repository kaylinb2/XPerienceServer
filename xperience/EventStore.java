package xperience;

/**
 * Shared interface for storing and retrieving Event objects.
 * Implemented by both in-memory and DB-backed storage.
 */
public interface EventStore {
    /**
     * Adds a new event if it does not already exist.
     * @param event The Event to add
     * @return true if added successfully, false if it already exists
     */
    boolean add(Event event);

    /**
     * Checks if an event by name already exists.
     * @param name The event name
     * @return true if event exists
     */
    boolean contains(String name);

    /**
     * Returns how many events are stored.
     */
    int size();
}
