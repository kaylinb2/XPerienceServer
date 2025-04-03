package xperience;

import donabase.DonaBaseConnection;
import donabase.DonaBaseException;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * 🗄️ XPerienceServerDB stores events in a MySQL database using DonaBase.
 */
public class XPerienceServerDB {

    private static final Logger logger = Logger.getLogger(XPerienceServerDB.class.getName());

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java xperience.XPerienceServerDB <port> <db_host>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String dbHost = args[1];

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("🟢 DB Server started on port " + port + ", connecting to DB at " + dbHost);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("📥 New client connected: " + clientSocket.getInetAddress());
                handleClient(clientSocket, dbHost);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "💥 Server error", e);
        }
    }

    private static void handleClient(Socket clientSocket, String dbHost) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "US-ASCII"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "US-ASCII"), true)
        ) {
            String input = in.readLine();
            logger.info("📨 Received: " + input);

            String response = validateAndStore(input, dbHost);
            out.println(response);
            logger.info("📤 Sent: " + response);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "❌ Error handling client", e);
        } finally {
            try {
                clientSocket.close();
                logger.info("🔌 Connection closed.");
            } catch (IOException ignored) {}
        }
    }

    private static String validateAndStore(String input, String dbHost) {
        if (input == null || !input.endsWith("#")) return "Reject#";

        String[] parts = input.split("#", -1);
        if (parts.length < 5) return "Reject#";

        String name = parts[0];
        String date = parts[1];
        String time = parts[2];
        String description = parts[3];

        if (name.isEmpty() || date.isEmpty() || time.isEmpty() || description.isEmpty()) {
            return "Reject#";
        }

        try (DonaBaseConnection db = new DonaBaseConnection(dbHost, 3306, "EventDB", "root", "")) {

            // Check if name already exists
            if (db.executeQuery("SELECT name FROM Event WHERE name = ?", name).next()) {
                return "Reject#";
            }

            // Insert event
            db.executeUpdate(
                    "INSERT INTO Event (name, date, time, description) VALUES (?, ?, ?, ?)",
                    name, date, time, description
            );

            // Count total events
            int count = db.executeQuery("SELECT COUNT(*) AS total FROM Event").getInt("total");
            return "Accept#" + count + "#";

        } catch (DonaBaseException e) {
            logger.warning("❗ DB error: " + e.getMessage());
            return "Reject#";
        }
    }
}
