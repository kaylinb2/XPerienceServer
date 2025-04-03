package xperience;

/**
 * 📦 Event class stores a single event's data.
 */
public class Event {
    public final String name;
    public final String date;
    public final String time;
    public final String description;

    public Event(String name, String date, String time, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s on %s at %s: %s", name, date, time, description);
    }
}
