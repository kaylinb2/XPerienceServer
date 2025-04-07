package xperience;

import donabase.DonaBaseConnection;
import java.util.List;
import java.util.ArrayList;

public class EventStoreDB implements EventStore {
    private DonaBaseConnection db;

    public EventStoreDB(DonaBaseConnection db) {
        this.db = db;
    }

    @Override
    public void add(Event e) {
        String sql = String.format(
            "INSERT INTO events VALUES ('%s', '%s', '%s', '%s')",
            e.name(), e.date(), e.time(), e.description()
        );

        try {
            db.insert(sql);
        } catch (Exception ex) {
            System.err.println("❌ Failed to insert event: " + ex.getMessage());
        }
    }

    @Override
    public int size() {
        try {
            var result = db.query("SELECT COUNT(*) FROM events");
            return Integer.parseInt(result.get(0).get(0));
        } catch (Exception e) {
            System.err.println("❌ Failed to get size: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Event> all() {
        List<Event> events = new ArrayList<>();
        try {
            var result = db.query("SELECT * FROM events");
            for (var row : result) {
                if (row.size() >= 4) {
                    Event e = new Event(row.get(0), row.get(1), row.get(2), row.get(3));
                    events.add(e);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to retrieve events: " + e.getMessage());
        }
        return events;
    }

    @Override
    public boolean contains(String name) {
        try {
            var result = db.query("SELECT COUNT(*) FROM events WHERE name = '" + name + "'");
            return Integer.parseInt(result.get(0).get(0)) > 0;
        } catch (Exception e) {
            System.err.println("❌ Failed to check contains: " + e.getMessage());
            return false;
        }
    }
}
