package whynot.com.communication;

import java.util.List;
import java.util.function.Consumer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import whynot.com.Interface.OnCategoriesRecived;
import whynot.com.Interface.RestCommunication;
import whynot.com.dto.DtoCategory;

public class Client {
    private static final String PATH = "";
    private static final Client ourInstance = new Client();

    public static Client getInstance() {
        return ourInstance;
    }
    private RestCommunication rest;

    private Client() {
        rest = new Retrofit.Builder().baseUrl(PATH).
                addConverterFactory(GsonConverterFactory.create()).build().create(RestCommunication.class);
    }

    public void getData(Consumer<List<DtoCategory>> successFunc) {
        Call<List<DtoCategory>> call = rest.getData();
        call.enqueue(new Callback<List<DtoCategory>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<DtoCategory>> call, Response<List<DtoCategory>> response) {
                List<DtoCategory> respond = response.body();
                successFunc.accept(respond);
            }

            @Override
            public void onFailure(Call<List<DtoCategory>> call, Throwable t) {

            }
        });
    }

//If method above will not work
//    public void getData(OnCategoriesRecived function) {
//        Call<List<DtoCategory>> call = rest.getData();
//        call.enqueue(new Callback<List<DtoCategory>>() {
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<List<DtoCategory>> call, Response<List<DtoCategory>> response) {
//                List<DtoCategory> respond = response.body();
//                function.received(respond);
//            }
//
//            @Override
//            public void onFailure(Call<List<DtoCategory>> call, Throwable t) {
//                function.failed();
//            }
//        });
//    }
}
