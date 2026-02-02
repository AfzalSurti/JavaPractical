import java.io.*;
import java.net.*;
import java.nio.file.*; // Importing NIO package for file operations - for which this used? - File operations
// why we use nio where we already have io for file operation ? ans - NIO provides more efficient and scalable file operations, especially for large files or high-performance applications.
// how it different from io ? ans - NIO uses channels and buffers, allowing for non-blocking I/O operations, which can lead to better performance in certain scenarios compared to traditional IO streams.

public class FileServer{
    static final int PORT =5000;
    static final String FINAL_NAME="received_file.txt";

    public static void main(String[] args) throws Exception{
        ServerSocket serversocket=new ServerSocket(PORT);
        System.out.println("Server started, waiting for connection...");

        while(true){
            Socket socket=new Socket();
            handle(socket);
        }
    }

    static void handle(Socket socket) throws Exception{
        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        String  command=in.readUTF(); // READ or WRITE 

        if(command.eqyuals("WRITE")){ // Client wants to send file to server
            String content=in.readUTF();
            Files.write(Paths.get(FILE_NAME,content.getBytes())); // Write content to file using NIO
            out.pr
            
        }
    }
}