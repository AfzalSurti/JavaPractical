import java.io.*;
import java.net.*;

public class UDPFileClient {

    public static void main(String[] args) {

        try {
            // 1. Create DatagramSocket on port 7000
            DatagramSocket socket = new DatagramSocket(7000);
            System.out.println("UDP Client ready...");

            FileOutputStream fos = new FileOutputStream("received_udp_file.txt");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());

                // 2. Check EOF
                if (msg.equals("EOF")) {
                    break;
                }

                // 3. Write data
                fos.write(packet.getData(), 0, packet.getLength());
            }

            System.out.println("File received using UDP");

            fos.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
