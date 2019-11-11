import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // establish connection with server
            Socket server = new Socket(serverAddress, 3124);

            // obtaining input and output streams
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());

            System.out.println("Server said -> " + in.readUTF());

            System.out.println("Idle for 60 seconds...");
            Thread.sleep(1000 * 60);

            System.out.println("Sending 'quit' command...");
            out.writeUTF("quit");

            server.close();
            in.close();
            out.close();
            System.out.println("Bye");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}