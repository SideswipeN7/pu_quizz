package whynot.com.communication;

import java.util.List;
import java.util.function.Consumer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import whynot.com.Interface.RestCommunication;
import whynot.com.dto.DtoCategory;
import whynot.com.dto.DtoGameData;

import static android.support.constraint.Constraints.TAG;

public class Client {
    private static final String ADDRESS = "http://quizz.gear.host";
    private static final Client instance_ = new Client();
    private RestCommunication rest_;

    /******************************************************************/
    /************************ Private Methods *************************/
    /*****************************************************************/

    private Client() {
        rest_ = new Retrofit.Builder().baseUrl(ADDRESS).
                addConverterFactory(GsonConverterFactory.create()).build().create(RestCommunication.class);
    }

    /******************************************************************/
    /************************ Public Methods *************************/
    /*****************************************************************/

    public static Client getInstance() {
        return instance_;
    }

    public void getData(Consumer<List<DtoCategory>> successFunc, Consumer<Void> errorFunc) {
        Call<List<DtoCategory>> call = rest_.getData();
        call.enqueue(new Callback<List<DtoCategory>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<DtoCategory>> call, Response<List<DtoCategory>> response) {
                List<DtoCategory> respond = response.body();
                Log.i(TAG, "onResponse: " + respond);
                successFunc.accept(respond);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Call<List<DtoCategory>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
                errorFunc.accept(null);
            }
        });
    }

    public void pushData(Consumer<Integer> successFunc, Consumer<Void> errorFunc, String nick, int points) {
        Call<Integer> call = rest_.pushData(new DtoGameData(nick, points));
        call.enqueue(new Callback<Integer>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer respond = response.body();
                successFunc.accept(respond);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                errorFunc.accept(null);
            }
        });
    }

    public void getTopTen(Consumer<List<DtoGameData>> successFunc, Consumer<Void> errorFunc) {
        Call<List<DtoGameData>> call = rest_.getTopten();
        call.enqueue(new Callback<List<DtoGameData>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<DtoGameData>> call, Response<List<DtoGameData>> response) {
                List<DtoGameData> respond = response.body();
                successFunc.accept(respond);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Call<List<DtoGameData>> call, Throwable t) {
                errorFunc.accept(null);
            }
        });
    }

//If method above will not work
//    public void getData(OnCategoriesRecived function) {
//        Call<List<DtoCategory>> call = rest_.getData();
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
}// class Client
