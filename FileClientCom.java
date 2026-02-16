import java.io.*;
import java.net.*;
import java.util.*;

public class FileClientCom {
    private static final int PORT = 5000;
    private static final String SERVER_IP = "localhost"; // ONLY ONE MACHINE (same PC)

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== FILE CLIENT MENU ======");
            System.out.println("1) WRITE  (keyboard -> server file)");
            System.out.println("2) UPDATE (append line on server file)");
            System.out.println("3) GET    (read updated file from server)");
            System.out.println("4) EXIT");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                // WRITE: take multi-line input and send to server
                System.out.println("\nEnter file content. Type END on new line to stop:");
                String content = readMultiline(sc);

                String response = sendWrite(SERVER_IP, content);
                System.out.println("Server Response: " + response);

            } else if (choice.equals("2")) {
                // UPDATE: take one line and append on server
                System.out.print("\nEnter one line to append: ");
                String line = sc.nextLine();

                String response = sendUpdate(SERVER_IP, line);
                System.out.println("Server Response: " + response);

            } else if (choice.equals("3")) {
                // GET: fetch file content and show
                String data = sendGet(SERVER_IP);
                System.out.println("\n----- FILE CONTENT FROM SERVER -----");
                System.out.println(data);

            } else if (choice.equals("4")) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("❌ Invalid choice. Enter 1,2,3,4 only.");
            }
        }

        sc.close();
    }

    // Reads multi-line input until user types END
    private static String readMultiline(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("END")) break;
            sb.append(line).append(System.lineSeparator()); // ok for lab (Linux). For cross-OS use System.lineSeparator()
        }
        return sb.toString();
    }

    // WRITE command
    private static String sendWrite(String ip, String content) {
        try (Socket s = new Socket(ip, PORT);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {

            out.writeUTF("WRITE");
            out.writeUTF(content);

            return in.readUTF();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // UPDATE command
    private static String sendUpdate(String ip, String extraLine) {
        try (Socket s = new Socket(ip, PORT);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {

            out.writeUTF("UPDATE");
            out.writeUTF(extraLine);

            return in.readUTF();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // GET command
    private static String sendGet(String ip) {
        try (Socket s = new Socket(ip, PORT);
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             DataInputStream in = new DataInputStream(s.getInputStream())) {

            out.writeUTF("GET");

            String status = in.readUTF();
            if (!status.equals("OK")) return status;

            return in.readUTF();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
