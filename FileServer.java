import java.io.*;
import java.net.*;

public class FileServer {

    public static void main(String[] args) {

        try {
            // 1. Create ServerSocket on port 5000
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started. Waiting for client...");

            // 2. Accept client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // 3. File to send
            File file = new File("server_file.txt");

            // 4. Read file using FileInputStream
            FileInputStream fis = new FileInputStream(file);

            // 5. Get output stream of socket
            OutputStream os = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            // 6. Send file data
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully");

            // 7. Close resources
            fis.close();
            os.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
