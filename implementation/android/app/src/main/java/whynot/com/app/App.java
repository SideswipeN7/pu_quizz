package whynot.com.app;

import whynot.com.dto.DtoAnswer;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class App {
    private static final App ourInstance = new App();
    private static final String[]  CONNECTING = {};
    private static final String[] SENDING_DATA = {};
    private Random random;
    static App getInstance() {
        return ourInstance;
    }

    private App() {
        //Most secure random
        random = new SecureRandom(String.valueOf(System.currentTimeMillis()).getBytes());
    }

    public String getRandomConnectingText(){
        return CONNECTING[random.nextInt(CONNECTING.length)];
    }

    public String getRandomPushingDataText(){
        return SENDING_DATA[random.nextInt(SENDING_DATA.length)];
    }

    public List<DtoAnswer> randomizeAnswers(List<DtoAnswer> list){
        Collections.shuffle(list);
        return list;
    }

}
