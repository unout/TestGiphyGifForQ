package a.testappgiphy.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import a.testappgiphy.R;


public class GifFullSizeFragment extends Fragment {

    private static final String GIF_URL = "url";
    private Context mContext;

    public GifFullSizeFragment() {
    }

    public static GifFullSizeFragment newInstance(String gifUrl) {
        GifFullSizeFragment fragment = new GifFullSizeFragment();
        Bundle args = new Bundle();
        args.putString(GIF_URL, gifUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    // Called when the fragment attaches to the context
    protected void onAttachToContext(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gif_full_size, container, false);
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        ImageView iv = v.findViewById(R.id.ivFullSize);

        if (getArguments() != null) {
            String gifUrl = getArguments().getString(GIF_URL);

            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;

            RequestOptions ro = new RequestOptions();
            ro.override(width, width);
            Glide.with(mContext)
                    .asGif()
                    .load(gifUrl)
                    .apply(ro)
                    .into(iv);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
