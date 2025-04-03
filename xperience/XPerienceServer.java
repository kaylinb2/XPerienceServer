package xperience;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 🟢 XPerience Server: Handles client connections, validates events, and checks passwords.
 */
public class XPerienceServer {
    private static final String REJECT_MSG = "Reject#❌";
    private static final String ACCEPT_MSG = "Accept#✅";
    private static PasswordList passwordList; // 🗝️ Password List to check for valid passwords

    public static void main(String[] args) throws IOException {
        // 🔑 Ensure there are exactly two arguments: port and password file
        if (args.length != 2) {
            System.err.println("Usage: java XPerienceServer <port> <password file>");
            return;
        }

        int port = Integer.parseInt(args[0]); // 🌐 Port number to listen on
        String passwordFile = args[1]; // 📖 Path to the password file

        // 📚 Initialize the password list from the file
        passwordList = new PasswordList(passwordFile);

        // 🖥️ Create the server socket and listen for incoming connections
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("🟢 XPerience Server started on port " + port);

        // 🔄 Accept client connections and handle them
        while (true) {
            Socket clientSocket = serverSocket.accept(); // 🤝 New client connected
            handleClient(clientSocket); // 💬 Handle client interaction
        }
    }

    /**
     * 🧑‍💻 Handle client requests by checking event and password validity.
     */
    public static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // 📥 Receive the event data from the client
            String request = in.readLine();
            System.out.println("📨 Received request from client: " + request);  // Log the incoming request

            // 🔪 Split event data into components
            String[] parts = request.split("#");

            // 🛑 Check if the event data is complete (requires 5 parts)
            if (parts.length != 5) {
                out.println(REJECT_MSG); // 🚫 Reject if not all parts are present
                System.out.println("📉 Invalid event data. Rejecting event.");  // Log rejection
                return;
            }

            // 📝 Extract event details from the request
            String name = parts[0];        // Event name
            String date = parts[1];        // Event date
            String time = parts[2];        // Event time
            String description = parts[3]; // Event description
            String password = parts[4];    // Event password

            // 🧐 Log the received password for debugging purposes
            System.out.println("📥 Received password: " + password);

            // 🔒 Check if the password is valid by comparing with the list
            if (!passwordList.contains(password)) {
                out.println(REJECT_MSG); // 🚫 Reject if password is invalid
                System.out.println("📉 Invalid password. Rejecting event.");  // Log rejection
                return;
            }

            // 🧹 Remove the password from the list after use (prevent reuse)
            passwordList.remove(password);
            System.out.println("🔑 Password valid and removed from list. Event accepted.");

            // ⚡ Proceed with event validation and acceptance
            out.println(ACCEPT_MSG + "1");  // ✅ Send back acceptance with a dummy event ID (1)
            System.out.println("📤 Event accepted. Sent response: Accept#1#");  // Log success
        } catch (IOException e) {
            e.printStackTrace(); // 🐞 Handle any IO errors
        }
    }
}
