package xperience;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 🛢️ EventStoreDB is responsible for managing event storage in MySQL.
 * It implements the EventStore interface to store and retrieve events.
 */
public class EventStoreDB implements EventStore {
    /** 🔗 Database connection instance */
    private final Connection conn;

    /**
     * 🏗️ Constructor for EventStoreDB
     * @param conn A valid SQL database connection
     */
    public EventStoreDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * ➕ Adds an event to the MySQL database.
     * @param event The event to be added.
     */
    @Override
    public void addEvent(Event event) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Event (name, date, time, description) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, event.getName());

            // ✅ Convert String to SQL Date and Time
            stmt.setDate(2, Date.valueOf(event.getDate())); // Convert YYYY-MM-DD to SQL Date
            stmt.setTime(3, Time.valueOf(event.getTime() + ":00")); // Convert HH:MM to SQL Time

            stmt.setString(4, event.getDescription());
            stmt.executeUpdate();

            System.out.println("✅ Event added successfully to database.");
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: Could not add event.");
            e.printStackTrace();
        }
    }

    /**
     * 🔍 Retrieves an event by name from the MySQL database.
     * @param name The name of the event.
     * @return The event object if found, or null if not.
     */
    @Override
    public Event getEvent(String name) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Event WHERE name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Event(
                    rs.getString("name"),
                    rs.getDate("date").toString(),  // Convert SQL Date back to String
                    rs.getTime("time").toString().substring(0,5), // Convert SQL Time back to HH:MM
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: Could not retrieve event.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 📜 Retrieves all events from the database.
     * @return A list of all events stored in the database.
     */
    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Event")) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                events.add(new Event(
                    rs.getString("name"),
                    rs.getDate("date").toString(),  
                    rs.getTime("time").toString().substring(0,5),  
                    rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: Could not retrieve all events.");
            e.printStackTrace();
        }
        return events;
    }
}
