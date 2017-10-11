package a.testappgiphy;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.List;

import a.testappgiphy.model.GIF;
import a.testappgiphy.model.GiphyResponse;
import a.testappgiphy.support.Constants;
import a.testappgiphy.support.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static a.testappgiphy.support.Constants.GIPHY_API_KEY;

public class Manager {

    private static volatile Manager instance;
    private OnUpdateListener mListener;
    private Service service;
    private GiphyResponse response;

    private Manager() {}

    public static Manager getInstance() {
        Manager man = instance;
        if (man == null) {
            synchronized (Manager.class) {
                man = instance;
                if (man == null) {
                    instance = man = new Manager();
                }
            }
        }
        return man;
    }

    private void setResponse(Response<GiphyResponse> response) {
        this.response = response.body();
    }

    public List<GIF> getResponse() {
        return response.getBody();
    }

    public interface OnUpdateListener {
        void onUpdateFinished(byte resultCode);
    }

    public void setListener(OnUpdateListener mListener) {
        this.mListener = mListener;
    }

    private byte calling(final Context context) {
        if (context != null && !NetworkUtils.isOnline(context)) {
            setInitFinished(Constants.CODE_NETWORK_ERROR);
            return Constants.CODE_NETWORK_ERROR;
        } else {
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(Constants.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = restAdapter.create(Service.class);
            return Constants.CODE_SUCCESS;
        }
    }

    private void enqueue(Call<GiphyResponse> call) {
        call.enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(@NonNull Call<GiphyResponse> call, @NonNull Response<GiphyResponse> response) {
                if (response.body() != null) {
                    Manager.getInstance().setResponse(response);
                    setInitFinished(Constants.CODE_SUCCESS);
                } else {
                    setInitFinished(Constants.EMPTY_BODY);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GiphyResponse> call, @NonNull Throwable t) {
                setInitFinished(Constants.CODE_COMMON_ERROR);
            }
        });
    }

    public void callingForTrends(final Context context) {
        if (calling(context) == 1) {
            enqueue(service.getTrends(GIPHY_API_KEY));
        }
    }

    public void callingForSearch(final Context context, String userInput) {
        if (calling(context) == 1) {
            enqueue(service.getSearchedGIF(userInput, GIPHY_API_KEY));
        }
    }

    private void setInitFinished(final byte resultCode) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                mListener.onUpdateFinished(resultCode);
            }
        });
    }
}