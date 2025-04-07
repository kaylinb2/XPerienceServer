package xperience;

import donabase.DonaBaseConnection;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class XPerienceServerDB {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: java XPerienceServerDB <port> <dbhost> <passwordfile>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String dbHost = args[1];
        String passwordFile = args[2];

        EventStore store = new EventStoreDB(new DonaBaseConnection(dbHost, 5432, "xperience", "xuser", "xpw"));
        PasswordList pwlist = new PasswordList(passwordFile);

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("🟢 XPerience DB Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(() -> handleClient(clientSocket, store, pwlist));
            }
        }
    }

    private static void handleClient(Socket socket, EventStore store, PasswordList passwordList) {
        try (
            Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.US_ASCII);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)
        ) {
            in.useDelimiter("#");

            String password = in.hasNext() ? in.next().trim() : null;
            String date = in.hasNext() ? in.next().trim() : null;
            String time = in.hasNext() ? in.next().trim() : null;
            String description = in.hasNext() ? in.next().trim() : null;

            if (password == null || date == null || time == null || description == null) {
                out.write("Reject#");
                out.flush();
                System.out.println("❌ Invalid event format. Rejecting.");
                return;
            }

            System.out.println("📨 Received request: " + password + "#" + date + "#" + time + "#" + description + "#");

if (!passwordList.validate(password, password)) {
    out.write("Reject#");
    out.flush();
    System.out.println("🔐 Invalid password for user: " + password);
    return;
}
passwordList.usePassword(password);

            Event event = new Event(password, date, time, description);
            int eventIndex = store.size(); // Get current size before adding
            store.add(event);
            System.out.println("✅ Storing event: " + event);
            out.write("Accept#" + (eventIndex + 1) + "#");
            out.flush();
        } catch (Exception e) {
            System.out.println("💥 Error handling client: " + e.getMessage());
        }
    }
}
