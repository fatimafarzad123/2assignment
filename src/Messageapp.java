interface MessageApp {
    void addContact(String name, String phone);
    void sendMessage(String contactName, String message);
    void deleteMessage(int index);
    void sortMessages();
    void displayMessages();
}

