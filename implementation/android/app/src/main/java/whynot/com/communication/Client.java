package whynot.com.communication;

class Client {
    private static final Client ourInstance = new Client();

    static Client getInstance() {
        return ourInstance;
    }

    private Client() {



    }
}
