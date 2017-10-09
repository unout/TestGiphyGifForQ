package a.testappgiphy.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GIF {

    @SerializedName("type")
    private String type;

    public String getId() {
        return id;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Drawable getDrawable(final String url, final String src) {

        final Drawable[] drawable = new Drawable[1];

        new Thread(new Runnable() {
            public void run() {
                //do time consuming operations
                try {
                    InputStream is = new URL(url).openStream();
                    drawable[0] = Drawable.createFromStream(is, src);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return drawable[0];
    }
}
//    "type": "gif",
//    "id": "tOWyML1WPzKjm",
//    "slug": "cheezburger-bears-tOWyML1WPzKjm",
//    "url": "https://giphy.com/gifs/cheezburger-bears-tOWyML1WPzKjm",
//    "bitly_gif_url": "http://gph.is/19r5Mfk",
//    "bitly_url": "http://gph.is/19r5Mfk",
//    "embed_url": "https://giphy.com/embed/tOWyML1WPzKjm",