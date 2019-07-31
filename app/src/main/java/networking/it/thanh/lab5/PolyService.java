package networking.it.thanh.lab5;

import networking.it.thanh.lab5.model.Chat;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface PolyService {


    @GET("wp-json/wp/v2/comments")
    Call<List<Chat>> getCategories(@Query("page") int page,
                                   @Query("per_page") int per_page);



}
