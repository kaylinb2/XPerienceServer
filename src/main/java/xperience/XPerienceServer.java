package xperience;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XPerienceServer {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java XPerienceServer <port> <passwordfile>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String passwordFile = args[1];

        System.out.println("🔐 Loading passwords from: " + passwordFile);

        EventStore store = new EventStoreMemory();
        PasswordList pwlist = new PasswordList(passwordFile);

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("🟢 XPerience Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(() -> handleClient(clientSocket, store, pwlist));
            }
        }
    }

private static void handleClient(Socket socket, EventStore store, PasswordList passwordList) {
    try (
        Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.US_ASCII);
        PrintWriter out = new PrintWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.US_ASCII), true
        )
    ) {
        // 👤 Receive login
        String username = in.nextLine().trim();
        System.out.println("👤 [recv] username: " + username);

        String password = in.nextLine().trim();
        System.out.println("🔒 [recv] password: " + password);

        // 🔐 Validate login
        if (!passwordList.validate(username, password)) {
            out.println("Reject#");
            System.out.println("❌ Login rejected: Reject#");
            return;
        }

        out.println("Aksept#");
        System.out.println("✅ Login accepted: " + username);

        // 📥 Receive and store events
        while (in.hasNextLine()) {
            String eventLine = in.nextLine().trim();
            System.out.println("📥 [recv] event: " + eventLine);

            String[] parts = eventLine.split("#");
            if (parts.length < 4) {
                out.println("Reject#");
                System.out.println("❌ Invalid event format from: " + username);
                continue;
            }

            String date = parts[0];
            String time = parts[1];
            String desc = parts[2];

            Event event = new Event(username, date, time, desc);
            int eventIndex = store.size();
            store.add(event);

            out.println("Aksept#" + (eventIndex + 1) + "#");
            System.out.println("📦 Stored event: " + event);
        }
    } catch (Exception e) {
        System.out.println("💥 Error handling client: " + e.getMessage());
    }
}
}
