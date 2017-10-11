package a.testappgiphy.support;


public class Constants {

    public static final String LOG = "|||||||||||||||||||";
    public static final String GIPHY_API_KEY = "dc6zaTOxFJmzC";

    public enum Error {
        SUCCESS,
        NETWORK_ERROR,
        COMMON_ERROR,
        EMPTY_BODY,
    }

    public static final String HOST = "http://api.giphy.com";
    public static final String PATH_TREND = "/v1/gifs/trending";
    public static final String PATH_SEARCH = "/v1/gifs/search";
    public static final String GIF_HOST = "https://media.giphy.com/media/";
    public static final String SMALL_GIF = "/200w_d.gif";
    public static final String FULL_GIF = "/giphy.gif";

}
