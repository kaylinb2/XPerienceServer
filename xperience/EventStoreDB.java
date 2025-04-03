package xperience;

import donabase.DonaBaseConnection;
import donabase.DonaBaseException;

import java.util.List;

/**
 * EventStoreDB implements EventStore using a MySQL backend via DonaBase.
 */
public class EventStoreDB implements EventStore {
    private final String host;
    private final int port;
    private final String dbName;
    private final String user;
    private final String password;

    public EventStoreDB(String host, int port, String dbName, String user, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean add(Event event) {
        try (DonaBaseConnection db = new DonaBaseConnection(host, port, dbName, user, password)) {
            if (contains(event.name())) return false;
            String sql = String.format("INSERT INTO Event(name, date, time, description) VALUES('%s', '%s', '%s', '%s')",
                    event.name(), event.date(), event.time(), event.description());
            db.insert(sql);
            return true;
        } catch (DonaBaseException e) {
            System.err.println("DB insert error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean contains(String name) {
        try (DonaBaseConnection db = new DonaBaseConnection(host, port, dbName, user, password)) {
            String query = String.format("SELECT name FROM Event WHERE name = '%s'", name);
            List<List<String>> result = db.query(query);
            return !result.isEmpty();
        } catch (DonaBaseException e) {
            System.err.println("DB contains check error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int size() {
        try (DonaBaseConnection db = new DonaBaseConnection(host, port, dbName, user, password)) {
            List<List<String>> result = db.query("SELECT COUNT(*) FROM Event");
            return Integer.parseInt(result.get(0).get(0));
        } catch (Exception e) {
            System.err.println("DB size error: " + e.getMessage());
            return 0;
        }
    }
}
