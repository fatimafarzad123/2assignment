
    import java.util.Date;

    class Message implements Comparable<Message> {
        String contactName;
        String message;
        Date time;

        public Message(String contactName, String message) {
            this.contactName = contactName;
            this.message = message;
            this.time = new Date();
        }

        @Override
        public int compareTo(Message other) {
            return this.time.compareTo(other.time);
        }

        @Override
        public String toString() {
            return "Contact: " + contactName + "\nMessage: " + message + "\nTime: " + time.toString();
        }
    }


