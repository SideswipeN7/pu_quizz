package whynot.com.communication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import whynot.com.dto.DtoCategory;

public interface RestCommunication {
    @GET("Android")
    public Call<List<DtoCategory>> getData();

    @POST("Android")
    public Call<Integer> pushData(@Body String nick);

    @GET("Android/Topten")
    public Call<List<String>> getToten();

}
