
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private Contact[] contacts;
    private Message[] messages;
    private int contactCount = 0;
    private int messageCount = 0;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Scanner scanner;

    public Client() {
        contacts = new Contact[10];
        messages = new Message[10];
        scanner = new Scanner(System.in);
    }

    public void initialize() throws IOException {
        socket = new Socket("localhost", 1000);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());


    }

    public void addContact(String name, String phone) {
        if (contactCount < contacts.length) {
            contacts[contactCount++] = new Contact(name, phone);
            System.out.println("Contact added: " + name);
        } else {
            System.out.println("Contact list is full!");
        }
    }

    public void sendMessage(String contactName, String message) {
        for (int i = 0; i < contactCount; i++) {
            if (contacts[i].name.equals(contactName)) {
                if (messageCount < messages.length) {
                    messages[messageCount++] = new Message(contactName, message);
                    System.out.println("Message sent to: " + contactName);

                } else {
                    System.out.println("Message list is full!");
                }
                return;
            }
        }
        System.out.println("Contact not found!");
    }

    public void deleteMessage(int index) {
        if (index >= 0 && index < messageCount) {
            for (int i = index; i < messageCount - 1; i++) {
                messages[i] = messages[i + 1];
            }
            messageCount--;
            System.out.println("Message deleted!");
        } else {
            System.out.println("Invalid message index!");
        }
    }

    public void sortMessages() {
        Arrays.sort(messages, 0, messageCount);
        System.out.println("Messages sorted by time!");
    }

    public void displayMessages() {
        if (messageCount == 0) {
            System.out.println("No messages to display.");
        } else {
            for (int i = 0; i < messageCount; i++) {
                System.out.println(messages[i]);
            }
        }
    }

    private void sendToServer(String message) {
        try {


            output.writeUTF(message);
            String response= input.readUTF();
            System.out.println("Server Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.initialize();
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Contact");
                System.out.println("2. Send Message");
                System.out.println("3. Delete Message");
                System.out.println("4. Sort Messages");
                System.out.println("5. Display Messages");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = client.scanner.nextInt();
                client.scanner.nextLine();

                switch (choice) {
                    case 1: {
                        System.out.print("Enter contact name: ");
                        String name = client.scanner.nextLine();
                        System.out.print("Enter contact phone number: ");
                        String phone = client.scanner.nextLine();
                        client.addContact(name, phone);
                        client.sendToServer("Added Contact: " + name + " (" + phone + ")");
                        break;
                    }
                    case 2: {
                        System.out.print("Enter contact name to send message: ");
                        String contactName = client.scanner.nextLine();
                        System.out.print("Enter your message: ");
                        String message = client.scanner.nextLine();
                        client.sendMessage(contactName, message);
                        client.sendToServer("Sent Message: " + message + " to " + contactName);
                        break;
                    }
                    case 3: {
                        System.out.print("Enter message index to delete (0 to " + (client.messageCount - 1) + "): ");
                        int index = client.scanner.nextInt();
                        client.scanner.nextLine();
                        client.deleteMessage(index);
                        client.sendToServer("Deleted Message at index " + index);
                        break;
                    }
                    case 4: {
                        client.sortMessages();
                        client.sendToServer("Sorted Messages");
                        break;
                    }
                    case 5: {
                        client.displayMessages();
                        client.sendToServer("Displayed Messages");
                        break;
                    }
                    case 6: {
                        System.out.println("Exiting...");
                        client.sendToServer("Exiting client");
                        client.socket.close();
                        return;
                    }
                    default:
                        System.out.println("Invalid option! Please choose again.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

