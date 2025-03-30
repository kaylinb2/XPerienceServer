package xperience;

public class Event {
    private String name;
    private String date;
    private String time;
    private String description;

    /** 
     * Constructor for Event.
     * @param name        The event name (must be unique).
     * @param date        The event date in YYYY-MM-DD format.
     * @param time        The event time in HH:MM format.
     * @param description A short event description.
     */
    public Event(String name, String date, String time, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    /** 
     * Gets the event name.
     * @return The event name.
     */
    public String getName() {
        return name;
    }

    /** 
     * Gets the event date.
     * @return The event date in YYYY-MM-DD format.
     */
    public String getDate() {
        return date;
    }

    /** 
     * Gets the event time.
     * @return The event time in HH:MM format.
     */
    public String getTime() {
        return time;
    }

    /** 
     * Gets the event description.
     * @return A short description of the event.
     */
    public String getDescription() {
        return description;
    }
}
