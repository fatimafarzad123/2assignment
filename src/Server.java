

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;

public class Server {

    static Message[] serverMessages = new Message[100];
    static int messageCount = 0;

    public static void main(String[] args) throws IOException {
        Client client2 = new Client();
        ServerSocket ss = new ServerSocket(1000);
        System.out.println("server (fatima) is waiting for connection....");
        Socket s = ss.accept();
        System.out.println("Connection is established now!");

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String input = in.readUTF();
                System.out.println("SERVER  says: " + input);

                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("Client ended the chat.");
                    break;
                }
                System.out.print("Enter your response: ");
                String response = br.readLine();
                out.writeUTF(response);
                out.flush();


                if (messageCount < 100) {
                    serverMessages[messageCount++] = new Message("Fatima", "hello!");
                }
            }
        } catch (SocketException e) {
            System.out.println("Connection closed by the client.");
        } finally {

            in.close();
            out.close();
            s.close();
            ss.close();
            System.out.println("Server has closed the connection.");
        }

    }
}
