import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerServer{
    public static void main(String[] args){
        final int port=9000;
        System.out.println("Echo Server is running on port "+port);
        try(ServerSocket ss=new ServerSocket(port)){
            System.out.println("Waiting for clients to connect...");
            try(Socket s=ss.accept();
            BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out=new PrintWriter(s.getOutputStream(),true)){
                System.out.println("client connected"+s.getInetAddress());
                String message;
                while((message=in.readLine())!=null){
                    System.out.println("Received from client: "+message);
                    
                    if(message.equalsIgnoreCase("exit")){
                        out.println("Goodbye!");
                        break;
                    }

                    out.println("echo: "+message);
                }
                System.out.println("Client disconnected: "+s.getInetAddress());
            }

        }catch(IOException e){
            System.err.println("Error: "+e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}