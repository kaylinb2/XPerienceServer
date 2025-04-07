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
            System.err.println("\u26a0\ufe0f Usage: java xperience.XPerienceTestClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        String username = "Safety";
        String password = "secure1";

        String[] events = {
            "2025-04-07#10:00#FirstTestEvent#HelloWorld",
            "2025-04-07#14:30#SecondTestEvent#SomeDetails",
            "2025-04-07#16:00#ThirdTestEvent#MoreData"
        };

        System.out.println("\uD83D\uDD0C Connecting to XPerienceServer on " + host + ":" + port);

        try (Socket socket = new Socket(host, port)) {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // Send username
            System.out.println("\uD83E\uDDD1 [send] username: " + username);
            out.write((username + "\n").getBytes(StandardCharsets.US_ASCII));
            out.flush();

            // Send password
            System.out.println("\uD83D\uDD10 [send] password: " + password);
            out.write((password + "\n").getBytes(StandardCharsets.US_ASCII));
            out.flush();

            // Read login response
            String loginResponse = readUntilNewline(in);
            System.out.println("\uD83D\uDCE8 Server login response: " + loginResponse);

            // ❌ FORCE FAILURE if response is not exactly "Accept#"
            if (!loginResponse.trim().equals("Accept#")) {
                throw new RuntimeException("❌ Expected 'Accept#' but got '" + loginResponse + "'");
            }

            System.out.println("\u2705 Login accepted: " + username);

            // Send events
            for (String event : events) {
                System.out.println("\uD83D\uDCE4 [send] event: " + event);
                out.write((event + "\n").getBytes(StandardCharsets.US_ASCII));
                out.flush();

                String eventResp = readUntilNewline(in);
                System.out.println("\uD83D\uDCE8 Server event response: " + eventResp);
            }

            System.out.println("\uD83D\uDD5A Finished sending all test events.");

        } catch (IOException e) {
            System.err.println("\uD83D\uDCA5 Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readUntilNewline(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int b = in.read();
            if (b == -1 || b == '\n') break;
            sb.append((char) b);
        }
        return sb.toString().trim();
    }
}
