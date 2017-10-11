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

import static a.testappgiphy.support.Constants.Error;
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

    public void setListener(OnUpdateListener mListener) {
        this.mListener = mListener;
    }

    private Error calling(final Context context) {
        if (context != null && !NetworkUtils.isOnline(context)) {
            setInitFinished(Error.NETWORK_ERROR);
            return Error.NETWORK_ERROR;
        } else {
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(Constants.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = restAdapter.create(Service.class);
            return Error.SUCCESS;
        }
    }

    private void enqueue(Call<GiphyResponse> call) {
        call.enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(@NonNull Call<GiphyResponse> call, @NonNull Response<GiphyResponse> response) {
                if (response.body() != null) {
                    Manager.getInstance().setResponse(response);
                    setInitFinished(Error.SUCCESS);
                } else {
                    setInitFinished(Error.EMPTY_BODY);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GiphyResponse> call, @NonNull Throwable t) {
                setInitFinished(Error.COMMON_ERROR);
            }
        });
    }

    public void callingForTrends(final Context context) {
        if (calling(context) == Error.SUCCESS) {
            enqueue(service.getTrends(GIPHY_API_KEY));
        }
    }

    public void callingForSearch(final Context context, String userInput) {
        if (calling(context) == Error.SUCCESS) {
            enqueue(service.getSearchedGIF(userInput, GIPHY_API_KEY));
        }
    }

    private void setInitFinished(final Error resultCode) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                mListener.onUpdateFinished(resultCode);
            }
        });
    }

    public interface OnUpdateListener {
        void onUpdateFinished(Error resultCode);
    }
}