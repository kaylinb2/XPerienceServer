/******************************************************
 * Author:     Kaylin Brooks
 * Assignment: Program 2 - XPerience Test Client
 * Class:      Software and System Security
 *
 * Description:
 * This client connects to the XPerienceServer (or XPerienceServerDB),
 * sends multiple event submissions, and prints the server's responses.
 ******************************************************/
package xperience;

import java.io.*;
import java.net.*;

/**
 * 🌟 XPerienceTestClient - Sends a test event to the XPerience Server.
 */
public class XPerienceTestClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("⚠️ Usage: java xperience.XPerienceTestClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // 📝 Example Event to Send
        String event = "Danooke#2025-02-12#20:00#Fusion of Karaoke and Dance#";

        System.out.println("🔌 Connecting to XPerienceServer on " + host + ":" + port);

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("📤 Sending event: " + event);
            out.println(event);

            String response = in.readLine();
            if (response != null) {
                if (response.startsWith("Accept")) {
                    System.out.println("✅ Accepted: " + response);
                } else {
                    System.out.println("❌ Rejected: " + response);
                }
            } else {
                System.out.println("⚠️ No response from server.");
            }

        } catch (IOException e) {
            System.err.println("💥 Connection failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("🔚 Client finished.");
    }
}
