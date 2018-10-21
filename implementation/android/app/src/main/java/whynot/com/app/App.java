package whynot.com.app;

import whynot.com.communication.Client;

class App {
    private static final App ourInstance = new App();

    static App getInstance() {
        return ourInstance;
    }

    private App() {
        Client.getInstance().getData((e->{

        }));
    }
}
