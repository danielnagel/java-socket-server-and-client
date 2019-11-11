import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private static int connectedClients = 0;

    public ClientHandler(Socket clientSocket, DataInputStream in, DataOutputStream out) {
        this.socket = clientSocket;
        this.in = in;
        this.out = out;
        connectedClients++;
    }

    @Override
    public void run() {
        System.out.println("Total clients connected: " + connectedClients);
        boolean isAlive = true;
        String received;
        String toReturn;
        while (isAlive) {
            try {
                // Send string to user
                out.writeUTF("Hello from socket server!");
                out.flush();

                // receive the answer from client
                received = in.readUTF();

                if (received.equals("quit")) {
                    isAlive = false;
                    connectedClients--;
                    System.out.println("Client disconnected.");
                    this.socket.close();
                    System.out.println("Total clients connected: " + connectedClients);
                } else {
                    // echo to client
                    toReturn = received;
                    out.writeUTF(toReturn);
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }

        try {
            // closing resources
            this.in.close();
            this.out.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}