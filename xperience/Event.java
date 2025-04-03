package xperience;

/**
 * Event class to represent an experience event.
 * Parses and serializes event information.
 */
public class Event {
    private final String name;       // Event name
    private final String date;       // Event date in YYYY-MM-DD
    private final String time;       // Event time in HH:MM (24-hour)
    private final String description; // Event description

    /**
     * Constructs an Event object with the given details.
     */
    public Event(String name, String date, String time, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    /**
     * Parses a message string and constructs an Event object.
     * Expected format: name#date#time#description
     */
    public static Event parse(String message) {
        String[] parts = message.split("#", 4);
        if (parts.length < 4) throw new IllegalArgumentException("Invalid format");
        return new Event(parts[0], parts[1], parts[2], parts[3]);
    }

    /**
     * Serializes the Event back to the string protocol format.
     */
    public String serialize() {
        return String.join("#", name, date, time, description);
    }

    public String name() {
        return name;
    }

    public String date() {
        return date;
    }

    public String time() {
        return time;
    }

    public String description() {
        return description;
    }
}
