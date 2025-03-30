/**************************c**********************
 * 💻 Author:     Kaylin Brooks
 * 📌 Assignment: Program 4 - XPerience Server
 * 🏫 Class:      Software and System Security
 * 
 * 📜 Description:
 * This program implements a multi-threaded server that listens for client
 * connections and processes event submissions. It accepts events with unique 
 * names and rejects duplicate submissions. The in-memory implementation 
 * does not persist data across restarts.
 ************************************************/

package xperience;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 * 🚀 The XPerienceServer handles client connections and processes event submissions.
 * This implementation uses in-memory storage, meaning events are lost upon restart.
 */
public class XPerienceServer {
    
    /** 📄 Logger instance to record server activity */
    private static final Logger logger = Logger.getLogger(XPerienceServer.class.getName());

    /** 🔍 A set to track event names and prevent duplicates */
    private static final Set<String> usedNames = new HashSet<>();
    
    /** 📂 A list to store accepted event objects */
    private static final List<Event> eventList = new ArrayList<>();
    
    /** 🎧 Port number on which the server will listen for connections */
    private static final int PORT = 8080;

    /**
     * 🚀 Main method to start the server and listen for client connections.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("⚡ Starting XPerience Server on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start(); // 🏗️ Handle each client in a separate thread
            }
        } catch (IOException e) {
            logger.severe("❌ Error starting server: " + e.getMessage());
        }
    }

    /**
     * 🖥️ Handles a client connection by processing incoming requests.
     * 
     * @param clientSocket The socket representing the client connection
     */
    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request = reader.readLine();
            
            if (request.startsWith("ADD_EVENT")) {
                processAddEvent(request, writer);
            } else if (request.startsWith("GET_EVENT")) {
                processGetEvent(request, writer);
            } else {
                writer.println("❌ ERROR: Unknown command");
            }
        } catch (IOException e) {
            logger.warning("⚠️ Client communication error: " + e.getMessage());
        }
    }

    /**
     * ➕ Processes an "ADD_EVENT" request by adding a new event if it is unique.
     * 
     * @param request The request string containing event details
     * @param writer  The PrintWriter to send responses to the client
     */
    private static void processAddEvent(String request, PrintWriter writer) {
        String[] parts = request.split("#");
        
        if (parts.length == 4) {
            String name = parts[1];
            String date = parts[2];
            String time = parts[3];

            // ✅ Ensure event name is unique
            if (usedNames.contains(name)) {
                writer.println("❌ ERROR: Event name already exists");
                return;
            }

            Event event = new Event(name, date, time, "Sample Description");
            eventList.add(event);
            usedNames.add(name);
            
            writer.println("✅ SUCCESS: Event added");
        } else {
            writer.println("❌ ERROR: Invalid event format");
        }
    }

    /**
     * 🔍 Processes a "GET_EVENT" request by retrieving the event details.
     * 
     * @param request The request string containing the event name
     * @param writer  The PrintWriter to send responses to the client
     */
    private static void processGetEvent(String request, PrintWriter writer) {
        String eventName = request.split("#")[1];

        for (Event event : eventList) {
            if (event.getName().equals(eventName)) {
                writer.println("📅 EVENT: " + event.getName() + " - " + event.getDate() + " " + event.getTime());
                return;
            }
        }
        writer.println("❌ ERROR: Event not found");
    }
}
