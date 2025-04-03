package xperience;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 * 🚀 XPerienceServer handles client connections, validates event submissions, and logs everything.
 */
public class XPerienceServer {

    private static final Logger logger = Logger.getLogger(XPerienceServer.class.getName());
    private static final Map<String, Event> eventMap = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("❌ Usage: java xperience.XPerienceServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("🟢 Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("📥 New client connected: " + clientSocket.getInetAddress());

                handleClient(clientSocket);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "💥 Server failed to start", e);
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "US-ASCII"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "US-ASCII"), true)
        ) {
            String input = in.readLine();
            logger.info("📨 Received: " + input);

            String response = validateEvent(input);
            out.println(response);  // ✅ This auto adds \n
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

    private static String validateEvent(String input) {
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

        synchronized (eventMap) {
            if (eventMap.containsKey(name)) return "Reject#";

            Event event = new Event(name, date, time, description);
            eventMap.put(name, event);
            return "Accept#" + eventMap.size() + "#";
        }
    }
}
