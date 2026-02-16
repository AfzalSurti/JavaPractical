import java.io.*;              // For DataInputStream, DataOutputStream, IOException
import java.net.*;             // For ServerSocket and Socket
import java.nio.file.*;        // For Files, Paths, StandardOpenOption (modern file operations)

public class FileServerCom {

    static final int PORT = 5000;                 // Port number where server will listen
    static final String FILE_NAME = "received_file.txt"; // File stored on server machine

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT); // Create server socket on PORT
            System.out.println("Server started on port " + PORT + ", waiting for connection...");

            while (true) { // Keep server running forever
                Socket socket = serverSocket.accept(); // ✅ Accept client connection (IMPORTANT)
                handle(socket); // Handle client request
            }

        } catch (IOException e) { // Catch network/file errors
            System.out.println("Server error: " + e.getMessage());
        }
    }

    static void handle(Socket socket) {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());   // Read data from client
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // Send data to client

            String command = in.readUTF(); // Read command: WRITE / UPDATE / GET

            if (command.equals("WRITE")) {
                String content = in.readUTF(); // Read full file content from client
                Files.write(Paths.get(FILE_NAME), content.getBytes()); // ✅ Write content to file
                out.writeUTF("OK: File received and saved successfully"); // Reply to client

            } else if (command.equals("UPDATE")) {
                String extraLine = in.readUTF(); // Read line to append
                Files.write(
                    Paths.get(FILE_NAME), // Target file path
                    (extraLine + System.lineSeparator()).getBytes(), // ✅ Append line + newline
                    StandardOpenOption.CREATE, // Create file if missing
                    StandardOpenOption.APPEND  // Append at end of file
                );
                out.writeUTF("OK: File updated successfully"); // Reply to client

            } else if (command.equals("GET")) {
                if (!Files.exists(Paths.get(FILE_NAME))) { // Check if file exists
                    out.writeUTF("ERROR: File does not exist"); // Send error to client
                } else {
                    String data = Files.readString(Paths.get(FILE_NAME)); // Read file content
                    out.writeUTF("OK");   // ✅ Status first
                    out.writeUTF(data);   // ✅ Then send file data
                }

            } else {
                out.writeUTF("ERROR: Unknown command"); // Unknown command handling
            }

        } catch (IOException e) {
            System.out.println("Client handling error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {} // Close socket safely
        }
    }
}
