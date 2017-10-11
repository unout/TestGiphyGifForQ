package a.testappgiphy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiphyResponse {

    @SerializedName("data")
    private List<GIF> body;

    public List<GIF> getBody() {
        return body;
    }
}
