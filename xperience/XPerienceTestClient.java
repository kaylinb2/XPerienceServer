/******************************************************
 * Author:     Kaylin Brooks
 * Assignment: Program 3 - XPerience Test Client
 * Class:      Software and System Security
 *
 * Description:
 * This client connects to the XPerienceServer (or XPerienceServerDB),
 * sends multiple event submissions, and prints the server's responses.
 ******************************************************/
package xperience;

import java.io.*;
import java.net.Socket;

public class XPerienceTestClient {
    public static void main(String[] args) {
        // Validate command-line arguments
        if (args.length != 2) {
            System.err.println("Usage: java xperience.XPerienceTestClient <host> <port>");
            System.exit(1);
        }

        // Parse command-line arguments
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // Test event submissions with various cases to test validation
        String[] testInputs = {
            "Danooke#02/12/2025#8pm#Fusion of Karaoke and Dance#",
            "Danooke#02/14/2025#9pm#Fusion of Dance and Karaoke#",  // Duplicate name
            "Mimey#02/14/2025#5pm#Poetry read by a mime#",
            "Alice#02/20/2025#7pm#Music Concert#",
            "Alice#02/21/2025#8pm#Live Band#",                     // Duplicate name
            "Bob#02/21/2025#8pm#",                                // Incomplete format
            "Charlie#02/22/2025|7pm|Poetry Reading#",             // Wrong delimiter
            "David#02/23/2025#6pm#Art Exhibition",                // Missing trailing #
            "Eva#02/25/2025#8pm#Talent#Show#",                    // Extra # in data
            "Frank#02/26/2025#9pm#Comedy Night#"
        };

        // Try-with-resources to ensure proper connection cleanup
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("🔌 Connected to XPerienceServer on " + host + ":" + port);

            // Send each test event and process the response
            for (String input : testInputs) {
                out.println("ADD_EVENT#" + input);
                String response = in.readLine();

                if (response == null) {
                    System.out.println("⚠️ No response from server.");
                } else if (response.startsWith("SUCCESS")) {
                    System.out.println("✅ " + response);
                } else if (response.startsWith("ERROR")) {
                    System.out.println("❌ " + response);
                } else {
                    System.out.println("⚠️ Unknown response: " + response);
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
