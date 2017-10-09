package a.testappgiphy;

import a.testappgiphy.model.GiphyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET(Constants.PATH_SEARCH)
    Call<GiphyResponse> getSearchedGIF(@Query("q") String userInput, @Query("api_key") String API_KEY);

    @GET(Constants.PATH_TREND)
    Call<GiphyResponse> getTrends(@Query("api_key") String API_KEY);
}
