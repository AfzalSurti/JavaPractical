import java.io.*;
import java.net.*;

public class UDPFileServer {

    public static void main(String[] args) {

        try {
            // 1. Create DatagramSocket on port 6000
            DatagramSocket socket = new DatagramSocket(6000);
            System.out.println("UDP Server started...");

            // 2. Read file
            File file = new File("server_file.txt");
            FileInputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            InetAddress clientAddress = InetAddress.getByName("localhost");
            int clientPort = 7000;

            // 3. Send file packets
            while ((bytesRead = fis.read(buffer)) != -1) {

                DatagramPacket packet =
                        new DatagramPacket(buffer, bytesRead, clientAddress, clientPort);

                socket.send(packet);
            }

            // 4. Send end-of-file signal
            DatagramPacket endPacket =
                    new DatagramPacket("EOF".getBytes(), 3, clientAddress, clientPort);
            socket.send(endPacket);

            System.out.println("File sent using UDP");

            fis.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
