package xperience;

import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Test XPerience Server (P2 version)
 * @version 1.1
 */
public class XPerienceServerTest2 {

    private static final Charset ENC = StandardCharsets.US_ASCII;

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.err.println("Parameter(s): <server> <port> [-eol (incorrect)]");
            System.exit(1);
        }

        String server = args[0];
        int port = Integer.parseInt(args[1]);
        boolean eol = (args.length == 3);

        interact(server, port, "Danoke#02/12/2025#8pm#Fusion of Karaoke and Dance#", eol);
        interact(server, port, "Dona Dance#02/14/2025#8pm#Dance like you don’t care#", eol);
        interact(server, port, "Dona Dance Dance#02/14/2025#9pm#Light the night#", eol);
        interact(server, port, "Danoke#02/12/2025#8pm#Fusion of Karaoke and Dance#", eol);
        interact(server, port, "Dona Dance#03/14/2025#8am#Dance dance like you don’t care#", eol);
        interact(server, port, "Dona Dance Dance#03/14/2025#8pm#Light the night#", eol);
        interact(server, port, "Safety#02/16/2025#8pm#Dance#Leave your friends#", eol);
        interact(server, port, "Safety#02/16/2025#8pm#Dance#", eol);
        interact(server, port, "SC" + "O".repeat(1025) + "RE#03/04/2025#4am#5#", eol);
        interact(server, port, "SC" + "O".repeat(1025) + "RE#03/05/2025#5am#6#", eol);
    }

    private static void interact(String server, int port, String send, boolean eol) {
        try (Socket clntSock = new Socket(server, port);
             PrintWriter out = new PrintWriter(clntSock.getOutputStream(), true, ENC)) {

            send(send + (eol ? "\n" : ""), out);
            print(clntSock.getInputStream().readAllBytes());

        } catch (Exception ex) {
            System.err.println("Client communication failed: " + ex.getMessage());
        }
    }

    private static void send(String sndStr, PrintWriter out) {
        int half = sndStr.length() / 2;
        out.write(sndStr.substring(0, half));
        out.flush();
        out.write(sndStr.substring(half));
        out.flush();
    }

    private static void print(byte[] buf) {
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] >= 32 && buf[i] < 127) {
                System.out.print((char) buf[i]);
            } else {
                System.out.print("[" + buf[i] + "]");
            }
        }
        System.out.println();
    }
}
