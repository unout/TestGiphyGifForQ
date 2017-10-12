package a.testappgiphy.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gif_full_size, container, false);

        ImageView iv = v.findViewById(R.id.ivFullSize);

        if (getArguments() != null) {
            String gifUrl = getArguments().getString(GIF_URL);

            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;

            RequestOptions ro = new RequestOptions();
            ro.override(width, width);
            Glide.with(getActivity())
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
