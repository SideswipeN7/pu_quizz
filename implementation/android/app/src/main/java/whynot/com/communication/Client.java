package whynot.com.communication;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import whynot.com.dto.DtoCategory;

public class Client {
    private static final Client ourInstance = new Client();

    public static Client getInstance() {
        return ourInstance;
    }

    private Client() {

    }
    public void getData(Consumer<List<DtoCategory>> function){

    }
}
