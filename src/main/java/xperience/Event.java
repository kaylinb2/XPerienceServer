package xperience;

public class Event {
    private String username;
    private String date;
    private String time;
    private String description;

    public Event(String username, String date, String time, String description) {
        this.username = username;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    // --- Methods your code might use: ---
    // e.name(), e.date(), e.time(), e.description():
    public String name()        { return username; }
    public String date()        { return date; }
    public String time()        { return time; }
    public String description() { return description; }

    // e.getName(), e.getDate(), e.getTime(), e.getDescription():
    public String getName()        { return username; }
    public String getDate()        { return date; }
    public String getTime()        { return time; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Event(" +
               "name=" + username + ", " +
               "date=" + date + ", " +
               "time=" + time + ", " +
               "desc=" + description + ")";
    }
}
