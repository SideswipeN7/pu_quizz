package whynot.com.Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import whynot.com.dto.DtoCategory;
import whynot.com.dto.DtoGameData;

public interface RestCommunication {
    @GET("Android")
    Call<List<DtoCategory>> getData();

    @POST("Android/PostResult")
    Call<Integer> pushData(@Body DtoGameData data);

    @GET("Android/GetResult")
    Call<List<DtoGameData>> getTopten();

}// interface RestCommunication
