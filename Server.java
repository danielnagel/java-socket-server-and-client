import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket socketServer = null;
        final int PORT = 3124;

        try {
            socketServer = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("IOException from 'socketServer = new ServerSocket(3124);'");
            e.printStackTrace();
        }

        System.out.printf("Socket server listening on port: %d \n\n", PORT);

        // running infinite loop for getting client requests
        while (true) {
            Socket client = null;

            try {
                // socket object to receive incoming client requests
                client = socketServer.accept();
                System.out.printf("A new client is connected: [addr=%s, port=%d]\n", client.getInetAddress(),
                        client.getPort());

                // obtaining input and output streams
                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                // create a new thread object
                Thread t = new ClientHandler(client, in, out);

                // invoking the start() method
                t.start();
            } catch (IOException e) {
                System.out.println("IOException from 'client = socketServer.accept();'");
                e.printStackTrace();
                try {
                    socketServer.close();
                } catch (IOException err) {
                    System.out.println("IOException from 'socketServer.close();'");
                    err.printStackTrace();
                }
            }
        }
    }
}