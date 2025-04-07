package xperience;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class XPerienceServerJUnitTest {
    private static final String LOGGERNAME = "XPerienceServerJUnitTest";
    private static final Logger logger = Logger.getLogger(LOGGERNAME);
    private static final String REJECT = "Reject#";
    private static final Charset ENC = StandardCharsets.US_ASCII;
    private static String server;
    private static int port;
    private Socket clntSock;
    private PrintWriter out;

    static void send(String sndStr, PrintWriter out) {
        int half = sndStr.length() / 2;
        out.write(sndStr.substring(0, half));
        out.flush();
        out.write(sndStr.substring(half));
        out.flush();
    }

    static boolean isPrintable(char c) {
        return (c >= 32 && c < 127);
    }

    @BeforeAll
    static void setUp() {
        try {
            server = Objects.requireNonNull(System.getProperty("server","localhost"));
            port = Integer.parseInt(System.getProperty("port" ,"5001"));
        } catch (Exception ex) {
            System.err.println("Test setup failed: " + ex);
            throw ex;
        }
    }

    static Stream<Arguments> testInteraction() {
        return Stream.of(
                arguments("Danoke#2025-02-12#20:00#Fusion of Karaoke and Dance#", "Accept#1#"),
                arguments("Dona Dance#2025-02-14#20:00#Dance like you don’t care#", "Accept#2#"),
                arguments("Dona Dance Dance#2025-02-14#21:00#Light the night#", "Accept#3#"),
                arguments("Danoke#2025-02-12#20:00#Fusion of Karaoke and Dance#", REJECT),
                arguments("Dona Dance#2025-03-14#08:00#Dance dance like you don’t care#", REJECT),
                arguments("Dona Dance Dance#2025-03-14#20:00#Light the night#", REJECT),
                arguments("Safety#2025-02-16#20:00#Dance#Leave your friends#", "Accept#4#"),
                arguments("Safety#2025-02-16#20:00#Dance#", REJECT),
                arguments("O".repeat(300) + "#2025-03-04#04:00#" + "D".repeat(1027) + "#", "Accept#5#"),
                arguments("O".repeat(300) + "#2025-05-04#05:00#" + "D".repeat(1027) + "#", REJECT)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testInteraction(String send, String expected) throws IOException {
        // Send message
        send(send, out);
        clntSock.shutdownOutput(); // ✅ tells server we are done sending

        // Read server response
        String response = readAllChars();
        assertTrue(response.startsWith(expected), "Expected %s but got %s".formatted(expected, response));
        if (!response.equals(expected)) {
            logger.log(Level.WARNING, () -> "Expected: " + expected + ", got: " + response);
        }
    }

    String readAllChars() throws IOException {
        byte[] buf = clntSock.getInputStream().readAllBytes();
        StringBuilder response = new StringBuilder();
        for (byte b : buf) {
            if (isPrintable((char) b)) {
                response.append((char) b);
            } else {
                response.append("[").append(b).append("]");
            }
        }
        return response.toString();
    }

    @BeforeEach
    void testSetUp() throws IOException {
        clntSock = new Socket(server, port);
        out = new PrintWriter(clntSock.getOutputStream(), true, ENC);
    }

    @AfterEach
    void testTearDown() throws IOException {
        out.close();
        clntSock.close();
    }
}
