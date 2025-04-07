/******************************************************
 * Author:     Kaylin Brooks
 * Assignment: Program 2 - XPerience Test Client
 * Class:      Software and System Security
 *
 * Description:
 * Connects to XPerienceServer, sends login and multiple events,
 * and prints server responses with emoji feedback.
 *
 * No buffering or readLine() allowed. We read until '\n'.
 ******************************************************/
package xperience;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class XPerienceTestClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("⚠️ Usage: java xperience.XPerienceTestClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // Hard-coded credentials
        String username = "Safety";
        String password = "secure1";

        // Multiple events to send
        String[] events = {
            "2025-04-07#10:00#FirstTestEvent#HelloWorld",
            "2025-04-07#14:30#SecondTestEvent#SomeDetails",
            "2025-04-07#16:00#ThirdTestEvent#MoreData"
        };

        System.out.println("🔌 Connecting to XPerienceServer on " + host + ":" + port);

        try (Socket socket = new Socket(host, port)) {
            // Raw byte streams: no buffering or readLine
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // 🧑 [send] username
            System.out.println("🧑 [send] username: " + username);
            out.write((username + "\n").getBytes(StandardCharsets.US_ASCII));
            out.flush();

            // 🔐 [send] password
            System.out.println("🔐 [send] password: " + password);
            out.write((password + "\n").getBytes(StandardCharsets.US_ASCII));
            out.flush();

            // 📨 Read login response
            String loginResponse = readUntilNewline(in);
            System.out.println("📨 Server login response: " + loginResponse);

            if (!loginResponse.startsWith("Accept")) {
                System.out.println("❌ Login rejected!");
                return; // stop if login fails
            } else {
                System.out.println("✅ Login accepted: " + username);
            }

            // 📤 Send multiple events
            for (String event : events) {
                System.out.println("📤 [send] event: " + event);
                out.write((event + "\n").getBytes(StandardCharsets.US_ASCII));
                out.flush();

                // 📝 Read each event response
                String eventResp = readUntilNewline(in);
                System.out.println("📨 Server event response: " + eventResp);
            }

            System.out.println("🔚 Finished sending all test events.");

        } catch (IOException e) {
            System.err.println("💥 Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reads bytes until encountering '\n'.
     * No buffering or readLine.
     */
    private static String readUntilNewline(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int b = in.read();
            if (b == -1) {
                // Stream closed
                break;
            }
            if (b == '\n') {
                // Found newline => done
                break;
            }
            sb.append((char) b);
        }
        return sb.toString().trim();
    }
}
