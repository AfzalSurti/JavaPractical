import java.io.*;
import java.net.*;

public class FileClient {

    public static void main(String[] args) {

        try {
            // 1. Connect to server (localhost for same machine)
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connected to server");

            // 2. Input stream from server
            InputStream is = socket.getInputStream();

            // 3. Save received file
            FileOutputStream fos = new FileOutputStream("received_file.txt");

            byte[] buffer = new byte[4096];
            int bytesRead;

            // 4. Receive file data
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("File received successfully");

            // 5. Close resources
            fos.close();
            is.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
