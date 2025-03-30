/************************************************
 * Author:     Kaylin Brooks
 * Assignment: Program 3 - XPerience Server
 * Class:      Software and System Security
 * 
 * Description:
 * This program implements a database-backed multi-threaded server
 * that listens for client connections, processes event submissions,
 * and retrieves event details from a MySQL database.
 ************************************************/

package xperience;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class XPerienceServerDB {
    private static final int PORT = 8081;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("⚡ Starting XPerience Server (Database-Backed) on port " + PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("❌ Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println("🍰 Received request: " + request);
                    if (request.startsWith("ADD_EVENT#")) {
                        String eventData = request.substring(10);  // Remove prefix
                        String response = processAddEvent(eventData);
                        out.println(response);
                    } else {
                        out.println("ERROR: Unknown command.");
                    }
                }
            } catch (IOException e) {
                System.err.println("❌ Client communication error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static String processAddEvent(String eventData) {
        String[] parts = eventData.split("#");

        if (parts.length != 4) {
            return "ERROR: Invalid event format.";
        }

        String name = parts[0];
        String date = parts[1];
        String time = parts[2];
        String description = parts[3];

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Event (name, date, time, description) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, description);
            stmt.executeUpdate();
            return "SUCCESS: Event added to the database.";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "ERROR: Duplicate event name - '" + name + "' already exists.";
        } catch (SQLException e) {
            return "ERROR: Database error - " + e.getMessage();
        }
    }
}
