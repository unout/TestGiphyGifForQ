package a.testappgiphy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiphyResponse {

    public List<GIF> getBody() {
        return body;
    }

    @SerializedName("data")
    private List<GIF> body;

}
