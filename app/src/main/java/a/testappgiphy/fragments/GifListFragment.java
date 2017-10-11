package a.testappgiphy.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import a.testappgiphy.Manager;
import a.testappgiphy.R;
import a.testappgiphy.adapters.RecyclerAdapter;
import a.testappgiphy.model.GIF;

public class GifListFragment extends Fragment implements Manager.OnUpdateListener {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private TextView tvSearch;

    public void setSearchText(String toSearch) {
        tvSearch.setText(toSearch);
    }

    public GifListFragment() {
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
        Manager.getInstance().setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gif_list, container, false);

        tvSearch = v.findViewById(R.id.tvSearch);

        mRecyclerView = v.findViewById(R.id.RVResult);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onUpdateFinished(byte resultCode) {
        switch (resultCode) {
            case 1:
                List<GIF> mGifs = Manager.getInstance().getResponse();
                if (mGifs.size() > 0) {
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mContext, mGifs);
                    mRecyclerView.setAdapter(recyclerAdapter);
                }
                break;
            case 2:
                String network_error = getString(R.string.connection_error);
                Toast.makeText(mContext, network_error, Toast.LENGTH_LONG).show();
                break;
            case 3:
                String common_error = getString(R.string.common_error);
                Toast.makeText(mContext, common_error, Toast.LENGTH_LONG).show();
                break;
            case 4:
                String empty_body = getString(R.string.empty_body);
                Toast.makeText(mContext, empty_body, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
