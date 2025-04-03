package xperience;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class XPerienceServerDB {
    private static final String REJECT_MSG = "Reject#❌";
    private static final String ACCEPT_MSG = "Accept#✅";

    private static PasswordList passwordList;

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java XPerienceServerDB <port> <db server> <password file>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String dbServer = args[1];
        String passwordFile = args[2];

        // 📖 Initialize password list
        passwordList = new PasswordList(passwordFile);

        // 🌐 Initialize database connection (replace with actual DB logic)
        // eventStore = new EventStoreDB(dbServer); 

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("🟢 XPerience Server (Database-Backed) started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
        }
    }

    // 🤖 Handle client connections and events
    public static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            String[] parts = request.split("#");

            if (parts.length != 5) {
                out.println(REJECT_MSG);
                return;
            }

            String name = parts[0];
            String date = parts[1];
            String time = parts[2];
            String description = parts[3];
            String password = parts[4];

            // 🔒 Check password
            if (!passwordList.contains(password)) {
                out.println(REJECT_MSG);
                return;
            }

            // 🧹 Remove the password from the list after use
            passwordList.remove(password);

            // ⚡ Process event (validation etc.)
            // Add any event validation logic here if needed
            out.println(ACCEPT_MSG + "1");  // 🚀 Assume 1 is the event ID for now
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
