package xperience;

import java.io.*;
import java.net.*;

/**
 * 🧪 XPerienceTestClient for testing your server responses manually.
 */
public class XPerienceTestClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java xperience.XPerienceTestClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        String[] testMessages = {
                "Danok#02/12/2025#8pm#Fusion of Karaoke and Dance#",
                "Danok#02/14/2025#8pm#Fusion of Dance and Karaoke#",
                "Mimometry#02/14/2025#7pm#Poetry “read” by a mime#"
        };

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            for (String msg : testMessages) {
                out.println(msg);
                String response = in.readLine();
                System.out.println("📤 Sent: " + msg);
                System.out.println("📥 Received: " + response);
            }

        } catch (IOException e) {
            System.err.println("❌ Test client error: " + e.getMessage());
        }
    }
}

